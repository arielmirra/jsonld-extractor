package utils

import java.io.{File, PrintWriter}
import net.liftweb.json.{DefaultFormats, JValue, prettyRender}

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

    def getId(json: JValue): String = {
        val url = (json \ "id").values.toString
        if (url.last == '/') url.split("/").dropRight(1).last
        else url.split("/").last
    }

    def save(items: Seq[JValue], path: String, format: String): Unit = {
        items.foreach(item => {
            if (item != null) writeFileContent(new File(path + getId(item) + format), prettyRender(item))
        })
    }

    def readFile(j: File): String = {
        import java.nio.file.Files
        Files.readString(j.toPath)
    }

    def getListOfFiles(dir: String): List[File] = {
        val d = new File(dir)
        if (d.exists && d.isDirectory) {
            d.listFiles.filter(_.isFile).toList
        } else {
            throw new IllegalArgumentException("folder path is wrong")
        }
    }
}

