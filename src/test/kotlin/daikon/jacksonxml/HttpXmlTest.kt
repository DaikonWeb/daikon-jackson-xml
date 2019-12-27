package daikon.jacksonxml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import daikon.HttpServer
import daikon.jacksonxml.Suit.*
import khttp.get
import khttp.post
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.MimeTypes.Type.TEXT_XML_UTF_8
import org.junit.jupiter.api.Test

class HttpJsonTest {

    @Test
    fun `serialize object to Json`() {
        val hand = BlackjackHand(
            Card('4', CLUBS), listOf(
                Card('1', DIAMONDS),
                Card('7', HEARTS)
            )
        )

        val expected = """<blackjackHand><hiddenCard><rank>4</rank><suit>CLUBS</suit></hiddenCard><visibleCards><card><rank>1</rank><suit>DIAMONDS</suit></card><card><rank>7</rank><suit>HEARTS</suit></card></visibleCards></blackjackHand>"""

        HttpServer()
            .get("/") { _, res -> res.xml(hand) }
            .start().use {
                val response = get("http://localhost:4545/")
                assertThat(response.headers["Content-Type"]).isEqualTo(TEXT_XML_UTF_8.asString())
                assertThat(response.text).isEqualTo(expected)
            }
    }

    @Test
    fun `deserialize Json to object`() {
        HttpServer()
            .post("/") { req, res -> res.xml(req.xml<BlackjackHand>()) }
            .start().use {
                val response = post(
                    url = "http://localhost:4545/",
                    data = """<blackjackHand><hiddenCard><rank>4</rank><suit>CLUBS</suit></hiddenCard><visibleCards><card><rank>1</rank><suit>DIAMONDS</suit></card><card><rank>7</rank><suit>HEARTS</suit></card></visibleCards></blackjackHand>"""
                )
                assertThat(response.text).isEqualTo("""<blackjackHand><hiddenCard><rank>4</rank><suit>CLUBS</suit></hiddenCard><visibleCards><card><rank>1</rank><suit>DIAMONDS</suit></card><card><rank>7</rank><suit>HEARTS</suit></card></visibleCards></blackjackHand>""")
            }
    }
}

@JacksonXmlRootElement(localName = "blackjackHand")
data class BlackjackHand(
    val hiddenCard: Card = Card(),
    @JacksonXmlElementWrapper(localName = "visibleCards") val card: List<Card> = mutableListOf()
)

data class Card(val rank: Char = '1', val suit: Suit = CLUBS)

enum class Suit {
    CLUBS, DIAMONDS, HEARTS;
}
