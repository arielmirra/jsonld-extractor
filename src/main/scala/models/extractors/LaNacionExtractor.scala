package models.extractors

import models.{JsonLD, NewsArticle}
import net.liftweb.json._
import utils.http

case class LaNacionExtractor() extends Extractor {
    val jsonLdSelector = "#Schema_NewsArticle"
    val articleSelector = "#cuerpo"

    override def extract(url: String): JsonLD = {
        val jsonLD = http.getElementContent(url, jsonLdSelector)
        val article = http.getElementTexts(url, articleSelector)

        val auxJson = Extractor.putId(parse(jsonLD), url)
        val jsonBody = s""" {\"articleBody\": \"$article\" } """
        val newsJson = auxJson merge parse(jsonBody)

        implicit val formats: DefaultFormats.type = DefaultFormats
        val newsArticle = newsJson.extract[NewsArticle]

        newsArticle
    }

    override def extract(urls: Seq[String]): Seq[JsonLD] = {
        var jsons = Seq[JsonLD]()
        urls.foreach(url => {
            val newJsonLd = extract(url)
            jsons = newJsonLd +: jsons
        })
        jsons
    }
}
