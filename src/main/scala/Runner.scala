import models.extractors.{ImdbExtractor, LaNacionExtractor}

object Runner extends App {
    val extractorLN = LaNacionExtractor()
    val extractorIMDB = ImdbExtractor()
    val urlLN = "https://www.lanacion.com.ar/politica/a-nid2353241"
    val urlIMDB = "http://www.imdb.com/title/tt0111161/"
//    extractorLN.extract(urlLN)
    extractorIMDB.extract(urlIMDB)
}
