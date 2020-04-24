import IMDBRunner.{extractor, links}
import models.extractors.{Extractor, ImdbExtractor, LaNacionExtractor}
import utils.Database

import scala.io.Source

//noinspection SourceNotClosed
object IMDBRunner extends App {
    val extractor = ImdbExtractor()
    val links = Source.fromFile("src/main/scala/resources/imdb-links.txt").getLines.toList
    val movies = Extractor.extract(links, extractor)
    Database.save(movies, "db/Movie/", ".json")
}

//noinspection SourceNotClosed
object LNRunner extends App {
    val extractor = LaNacionExtractor()
    val links = Source.fromFile("src/main/scala/resources/la-nacion-links.txt").getLines.toList
    val articles = Extractor.extract(links, extractor)
    Database.save(articles, "db/NewsArticle/", ".json")
}
