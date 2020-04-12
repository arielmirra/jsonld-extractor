package utils

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

object http {
    def getElementContent(url: String, selector: String): String = {
        val browser = JsoupBrowser()
        val doc = browser.get(url)
        val docElement = doc >> element(selector)
        docElement.innerHtml
    }

    def getElementTexts(url: String, selector: String): String = {
        val browser = JsoupBrowser()
        val doc = browser.get(url)
        val docTexts = doc >> element(selector) >> texts("p")
        val addTexts: (String, String) => String = (a, b) => a + "\n" + b
        val textsString = docTexts.foldLeft("")(addTexts)
        textsString.replace("\"", "'")
    }
}
