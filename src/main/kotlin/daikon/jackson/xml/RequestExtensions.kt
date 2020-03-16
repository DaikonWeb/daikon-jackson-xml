package daikon.jackson.xml

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import daikon.core.Request

inline fun <reified T : Any> Request.xml(xmlMapper: XmlMapper = XmlMapper.builder().build()): T {
    return xmlMapper.readValue(body().trim(), T::class.java)
}
