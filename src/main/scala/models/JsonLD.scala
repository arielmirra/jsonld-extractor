package models

trait JsonLD {
    def `@context`: String

    def id: String

    def `@type`: String
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
                          articleBody: String
                      ) extends JsonLD

case class Movie(
                    `@context`: String,
                    id: String,
                    `@type`: String,
                    url: String,
                    name: String,
                    image: String,
                    datePublished: String,
                    keywords: String,
                    duration: String,
                    genre: String,
                    contentRating: String,
                    actor: Seq[Person],
                    director: Person,
                ) extends JsonLD

class Person(
                     url: String,
                     name: String
                 ) extends JsonLD {
    val `@context`: String = "http://schema.org"
    val id: String = url
    val `@type`: String = "Person"
}
