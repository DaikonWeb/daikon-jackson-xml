package daikon.jackson.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import daikon.HttpServer
import daikon.jackson.xml.Suit.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import topinambur.http

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
                val response = "http://localhost:4545/".http.get()
                assertThat(response.header("Content-Type")).isEqualTo(TEXT_XML_UTF_8)
                assertThat(response.body).isEqualTo(expected)
            }
    }

    @Test
    fun `deserialize Json to object`() {
        HttpServer()
            .post("/") { req, res -> res.xml(req.xml<BlackjackHand>()) }
            .start().use {
                val response = "http://localhost:4545/".http.post(
                    body = """<blackjackHand><hiddenCard><rank>4</rank><suit>CLUBS</suit></hiddenCard><visibleCards><card><rank>1</rank><suit>DIAMONDS</suit></card><card><rank>7</rank><suit>HEARTS</suit></card></visibleCards></blackjackHand>"""
                )
                assertThat(response.body).isEqualTo("""<blackjackHand><hiddenCard><rank>4</rank><suit>CLUBS</suit></hiddenCard><visibleCards><card><rank>1</rank><suit>DIAMONDS</suit></card><card><rank>7</rank><suit>HEARTS</suit></card></visibleCards></blackjackHand>""")
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
