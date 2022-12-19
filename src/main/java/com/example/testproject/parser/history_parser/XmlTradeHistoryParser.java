package com.example.testproject.parser.history_parser;

import com.example.testproject.model.TradingHistory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

@Component
public class XmlTradeHistoryParser {
    private final XmlHistorySaxHandler handler;
    private final SAXParser parser;

    public XmlTradeHistoryParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        this.parser = factory.newSAXParser();
        this.handler = new XmlHistorySaxHandler();
    }

    public List<TradingHistory> parse(String fileName) throws IOException, SAXException {
        parser.parse(fileName, handler);
        return handler.getTradingHistories();
    }
}
