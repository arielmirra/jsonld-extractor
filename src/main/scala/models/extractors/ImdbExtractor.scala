package models.extractors

import models.{JsonLD, NewsArticle}
import net.liftweb.json.{DefaultFormats, parse}
import utils.http

case class ImdbExtractor() extends Extractor {
    val jsonLdSelector = "script[type=\"application/ld+json\"]"

    override def extract(url: String): JsonLD = {
        val jsonLD = http.getJsonLdString(url)
        println(jsonLD)

        implicit val formats: DefaultFormats.type = DefaultFormats
//        val newsArticle = newsJson.extract[NewsArticle]

        null
    }
}
