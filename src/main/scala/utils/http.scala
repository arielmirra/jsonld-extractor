package utils

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

object http {
    private def getDoc(url: String) = {
        val browser: Browser = JsoupBrowser()
        browser.get(url)
    }

    def getElementContent(url: String, selector: String): String = {
        val docElement = getDoc(url) >> element(selector)
        docElement.innerHtml
    }

    def getElementTexts(url: String, selector: String): String = {
        val docTexts = getDoc(url) >> element(selector) >> texts("p")
        val addTexts: (String, String) => String = (a, b) => a + "\n" + b
        val textsString = docTexts.foldLeft("")(addTexts)
        textsString.replace("\"", "'")
    }

    def getJsonLdString(url: String): String = {
        val jsonLdSelector = "script[type=\"application/ld+json\"]"
        val optionElement = getDoc(url) >?> element(jsonLdSelector)
        if (optionElement.isDefined) optionElement.get.innerHtml
        else throw new Exception(s"there is no json-ld in $url")
    }
}
