package com.example.testproject.parser.history_parser;

import com.example.testproject.model.TradingHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class XmlHistorySaxHandler extends DefaultHandler {
    @Getter
    private List<TradingHistory> tradingHistories = new ArrayList<>();
    private TradingHistory history;

    /**
     * Выполняется при нахождении начала элемента <row></row>
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("row")) {
            history = new TradingHistory();

            String trade_data = (attributes.getValue("TRADEDATE"));
            if (trade_data != null) history.setTradeDate(LocalDate.parse(trade_data));

            String secId = attributes.getValue("SECID");
            if (secId != null) history.setSecId(secId);

            String num_trades = attributes.getValue("NUMTRADES");
            if (num_trades != null) history.setNumTrades(Long.valueOf(num_trades));

            String open = attributes.getValue("OPEN");
            if (open != null && open.length() != 0) {
                history.setOpen(Double.valueOf(open));
            } else history.setOpen(0.0);

            String close = attributes.getValue("CLOSE");
            if (close != null && close.length() != 0) {
                history.setClose(Double.valueOf(close));
            } else history.setClose(0.0);


            if (history != null) tradingHistories.add(history);
        }
    }
}
