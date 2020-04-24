package models.extractors

import net.liftweb.json.{parse, prettyRender}
import utils.http

case class ImdbExtractor() extends Extractor {
    val jsonLdSelector = "script[type=\"application/ld+json\"]"

    override def extract(url: String): String = {
        val jsonLD = http.getJsonLdString(url)
        val auxJson = parse(jsonLD)
        val movieJson = Extractor.putId(auxJson, url)
        prettyRender(movieJson)
    }
}
