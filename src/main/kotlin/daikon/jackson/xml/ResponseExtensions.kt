package daikon.jackson.xml

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import daikon.core.Response

const val TEXT_XML_UTF_8 = "text/xml;charset=utf-8"

fun Response.xml(value: Any) {
    this.type(TEXT_XML_UTF_8)
    val xml = XmlMapper.builder().build().writeValueAsString(value)
    this.write(xml)
}
