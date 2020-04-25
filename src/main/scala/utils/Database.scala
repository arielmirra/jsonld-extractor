package utils

import java.io.{File, PrintWriter}
import net.liftweb.json.{DefaultFormats, JValue, prettyRender, parse}

object Database {

    implicit val formats: DefaultFormats.type = DefaultFormats

    private def writeFileContent(file: File, content: Object): Unit = {

        def validatePath(file: File): Unit = {
            val filePath = file.getAbsoluteFile.getParentFile
            if (!filePath.exists()) filePath.mkdirs()
        }

        validatePath(file)

        var pw: PrintWriter = null
        try {
            pw = new PrintWriter(file)
            pw.write(content.toString)
        } catch {
            case e: Exception => println(s"Error writing file $file, \n${e.getMessage}")
        } finally {
            if (pw != null) {
                pw.close()
            }
        }
    }

    private def getId(json: JValue): String = {
        val url = (json \ "id").values.toString
        if (url.last == '/') url.split("/").dropRight(1).last
        else url.split("/").last
    }

    private def readFile(j: File): String = {
        import java.nio.file.Files
        Files.readString(j.toPath)
    }

    private def getListOfFiles(dir: String): List[File] = {
        val d = new File(dir)
        if (d.exists && d.isDirectory) {
            d.listFiles.filter(_.isFile).toList
        } else {
            throw new IllegalArgumentException("folder path is wrong")
        }
    }

    private def getIdsList(containerContent: List[JValue]): String = {
        var result = ""
        containerContent.foreach(json => {
            val id =
                s"""
                   |        {
                   |            "@id": "${(json \ "id").values.toString}"
                   |        },""".stripMargin
            result += id
        })
        result
    }

    private def getContainerString(containerName: String, containerContent: List[JValue]): String = {
        val content = getIdsList(containerContent)
        s"""
           |{
           |    "@id": "http://localhost:8080/$containerName",
           |    "@type": [
           |        "http://www.w3.org/ns/ldp#BasicContainer"
           |    ],
           |    "http://purl.org/dc/terms/title": [
           |        {
           |            "@value": "Container of $containerName resources"
           |        }
           |    ],
           |    "http://www.w3.org/ns/ldp#contains": [
           |        ${content dropRight 1}
           |    ]
           |}
           |""".stripMargin
    }

    def save(items: Seq[JValue], path: String, format: String): Unit = {
        items.foreach(item => {
            if (item != null) writeFileContent(new File(path + getId(item) + format), prettyRender(item))
        })
    }

    def getContainerResource(container: String, resource: String): String = {
        try {
            val files = getListOfFiles(s"db/$container")
            val resourceOptional = files.find(f => f.getName.equals(resource + ".json"))
            if (resourceOptional.isDefined) readFile(resourceOptional.get)
            else throw new IllegalArgumentException
        } catch {
            case e: IllegalArgumentException => s"resource $resource not found"
        }
    }

    def getContainer(name: String): String = {
        try {
            val files = getListOfFiles(s"db/$name")
            val jsons = files.map(f => parse(readFile(f)))
            getContainerString(name, jsons)
        } catch {
            case e: IllegalArgumentException => s"resource $name not found"
        }
    }
}

