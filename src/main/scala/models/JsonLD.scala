package models

trait JsonLD {
    val `@context`: String
    val id: String
    val `@type`: String
}

case class NewsArticle(
                          `@context`: String,
                          id: String,
                          `@type`: String,
                          url: String,
                          dateCreated: String,
                          datePublished: String,
                          dateModified: String,
                          articleSection: String,
                          author: Seq[String],
                          creator: Seq[String],
                          keywords: Seq[String],
                          body: String
                      ) extends JsonLD
