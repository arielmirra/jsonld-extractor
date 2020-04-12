package models.extractors

import models.{JsonLD, Movie}
import net.liftweb.json.{DefaultFormats, parse, prettyRender}
import utils.http

case class ImdbExtractor() extends Extractor {
    val jsonLdSelector = "script[type=\"application/ld+json\"]"

    override def extract(url: String): JsonLD = {
        val jsonLD = http.getJsonLdString(url)
        val auxJson = parse(jsonLD)
        val movieJson = Extractor.putId(auxJson, url)
        println(prettyRender(movieJson))
        implicit val formats: DefaultFormats.type = DefaultFormats
        val movie = movieJson.extract[Movie]
        println(movie)
        movie
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
