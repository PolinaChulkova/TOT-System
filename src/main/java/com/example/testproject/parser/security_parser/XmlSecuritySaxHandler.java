package com.example.testproject.parser.security_parser;

import com.example.testproject.model.Security;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class XmlSecuritySaxHandler extends DefaultHandler {
    @Getter
    private List<Security> securities = new ArrayList<>();
    @Getter
    private Security security;

    /**
     * Выполняется при нахождении начала элемента <row></row>
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equals("row")) {
            security = new Security();

            String secId = attributes.getValue("secid");
            if (secId != null) security.setSecId(secId);

            String regNumber = attributes.getValue("regnumber");
            if (regNumber != null) security.setRegNumber(regNumber);

            String name = attributes.getValue("shortname");
            if (name != null) security.setName(name);

            String emitentTitle = attributes.getValue("emitent_title");
            if (emitentTitle != null) security.setEmitentTitle(emitentTitle);
            if (security != null) securities.add(security);
        }
    }
}
