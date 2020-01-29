package daikon.jackson.xml

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import daikon.Response
import org.eclipse.jetty.http.MimeTypes.Type.TEXT_XML_UTF_8

fun Response.xml(value: Any) {
    this.type(TEXT_XML_UTF_8.asString())
    val xml = XmlMapper.builder().build().writeValueAsString(value)
    this.write(xml)
}
