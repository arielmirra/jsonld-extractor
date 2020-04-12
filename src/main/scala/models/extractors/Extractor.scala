package models.extractors

import models.JsonLD
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.parse

trait Extractor {
    def extract(url: String): JsonLD
    def extract(urls: Seq[String]): Seq[JsonLD]
}

object Extractor {
    def putId(json: JValue, id: String): JValue = {
        val stringId: String = s""" {\"id\": \"$id\" } """
        val jsonId = parse(stringId)
        val result = jsonId merge json
        result
    }
}
