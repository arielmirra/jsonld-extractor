package models.extractors

import net.liftweb.json.{parse, prettyRender}
import utils.http

case class LaNacionExtractor() extends Extractor {
    val jsonLdSelector = "#Schema_NewsArticle"
    val articleSelector = "#cuerpo"

    override def extract(url: String): String = {
        val jsonLD = http.getElementContent(url, jsonLdSelector)
        val article = http.getElementTexts(url, articleSelector)

        val auxJson = Extractor.putId(parse(jsonLD), url)
        val jsonBody = s""" {\"articleBody\": \"$article\" } """
        val newsJson = auxJson merge parse(jsonBody)
        prettyRender(newsJson)
    }
}
