package com.example.testproject.parser.security_parser;

import com.example.testproject.model.Security;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class XmlSecurityParser {

    private final XmlSecuritySaxHandler handler;
    private final SAXParser parser;

    public XmlSecurityParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        this.parser = factory.newSAXParser();
        this.handler = new XmlSecuritySaxHandler();
    }

    public List<Security> parse(String fileName) throws IOException, SAXException {
        parser.parse(fileName, handler);
        return handler.getSecurities();
    }
    public Security parse(InputStream inputStream) throws IOException, SAXException {
        parser.parse(inputStream, handler);
        return handler.getSecurity();
    }
}
