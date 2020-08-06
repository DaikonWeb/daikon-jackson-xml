# Daikon Jackson Xml

![Daikon](./logo.svg)

Daikon Jackson Xml is a library that add to Daikon the ability to handle Xml requests and responses.

The main goals are:
* Help in rendering an object as Xml in the response
* Help in parsing a Xml request to an object

## How to add Daikon Jackson Xml to your project
[![](https://jitpack.io/v/DaikonWeb/daikon-jackson-xml.svg)](https://jitpack.io/#DaikonWeb/daikon-jackson-xml)

### Gradle
- Add JitPack in your root build.gradle at the end of repositories:
```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```
- Add the dependency
```
implementation('com.github.DaikonWeb:daikon-jackson-xml:1.8.0')
```

### Maven
- Add the JitPack repository to your build file 
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
- Add the dependency
```
<dependency>
    <groupId>com.github.DaikonWeb</groupId>
    <artifactId>daikon-jackson-xml</artifactId>
    <version>1.8.0</version>
</dependency>
```

## How to use
```
@JacksonXmlRootElement(localName = "blackjackHand")
data class BlackjackHand(
    val hiddenCard: Card = Card(),
    @JacksonXmlElementWrapper(localName = "visibleCards") 
    val card: List<Card> = mutableListOf()
)

data class Card(val rank: Char = '1', val suit: Suit = CLUBS)

enum class Suit {
    CLUBS, DIAMONDS, HEARTS;
}

HttpServer()
    .post("/") { req, res -> res.xml(req.xml<BlackjackHand>()) }
    .start().use {
        val response = post(
            url = "http://localhost:4545/",
            data = """<blackjackHand><hiddenCard><rank>4</rank><suit>CLUBS</suit></hiddenCard><visibleCards><card><rank>1</rank><suit>DIAMONDS</suit></card><card><rank>7</rank><suit>HEARTS</suit></card></visibleCards></blackjackHand>"""
        )
        assertThat(response.text).isEqualTo("""<blackjackHand><hiddenCard><rank>4</rank><suit>CLUBS</suit></hiddenCard><visibleCards><card><rank>1</rank><suit>DIAMONDS</suit></card><card><rank>7</rank><suit>HEARTS</suit></card></visibleCards></blackjackHand>""")
    }
```

## Resources
* Documentation: https://daikonweb.github.io
* Examples: https://github.com/DaikonWeb/daikon-examples
* Jackson Dataformat Xml project: https://github.com/FasterXML/jackson-dataformat-xml

## Authors

* **[Marco Fracassi](https://github.com/fracassi-marco)**

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details
