import models.extractors.{ImdbExtractor, LaNacionExtractor}

import scala.io.Source

//noinspection SourceNotClosed
object IMDBRunner extends App {
    val extractor = ImdbExtractor()
    val links = Source.fromFile("src/main/scala/resources/imdb-links.txt").getLines.toList
    links.foreach(url => {

    })
}

//noinspection SourceNotClosed
object LNRunner extends App {
    val extractor = LaNacionExtractor()
    val links = Source.fromFile("src/main/scala/resources/la-nacion-links.txt").getLines.toList
    links.foreach(extractor.extract)
}
