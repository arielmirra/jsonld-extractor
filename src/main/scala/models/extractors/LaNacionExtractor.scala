package models.extractors

import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.parse
import org.jsoup.HttpStatusException
import utils.http

case class LaNacionExtractor() extends Extractor {
    val jsonLdSelector = "#Schema_NewsArticle"
    val articleSelector = "#cuerpo"

    override def extract(url: String): JValue = {
        var jsonLD = ""
        try {
            jsonLD = http.getElementContent(url, jsonLdSelector)
        } catch {
            case e: HttpStatusException =>
                println(s"error: ${e.getStatusCode}")
                return null
        }

        val article = http.getElementTexts(url, articleSelector)

        val auxJson = Extractor.putId(parse(jsonLD), url)
        val jsonBody = s""" {\"articleBody\": \"$article\" } """
        val newsJson = auxJson merge parse(jsonBody)
        newsJson
    }
}
