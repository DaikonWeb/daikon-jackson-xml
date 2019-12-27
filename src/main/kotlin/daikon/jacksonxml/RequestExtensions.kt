package daikon.jacksonxml

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import daikon.Request

inline fun <reified T : Any> Request.xml(xmlMapper: XmlMapper = XmlMapper.builder().build()): T {
    return xmlMapper.readValue(body().trim(), T::class.java)
}
