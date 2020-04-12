import models.extractors.LaNacionExtractor

object Runner extends App {
    val extractor = LaNacionExtractor()
    val url = "https://www.lanacion.com.ar/politica/a-nid2353241"
    extractor.extract(url)
}
