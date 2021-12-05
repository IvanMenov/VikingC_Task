package task.vikingc.components;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class MyXMLMapper {

	private static XmlMapper mapper;

	public XmlMapper getXMLMapper() {
		if (mapper == null) {
			mapper = new XmlMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
		return mapper;
	}
}
