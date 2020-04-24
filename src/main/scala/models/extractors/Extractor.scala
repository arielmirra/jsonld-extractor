package models.extractors

import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.parse

trait Extractor {
    def extract(url: String): String
}

object Extractor {
    def putId(json: JValue, id: String): JValue = {
        val stringId: String = s""" {\"id\": \"$id\" } """
        val jsonId = parse(stringId)
        val result = jsonId merge json
        result
    }

    def extract(urls: Seq[String], extractor: Extractor): Seq[String] = {
        var jsons = Seq[String]()
        urls.foreach(url => {
            val newJsonLd = extractor.extract(url)
            jsons = newJsonLd +: jsons
        })
        jsons
    }
}
