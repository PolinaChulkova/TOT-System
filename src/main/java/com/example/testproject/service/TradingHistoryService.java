package com.example.testproject.service;

import com.example.testproject.dto.SummaryData;
import com.example.testproject.dto.TradingHistoryDto;
import com.example.testproject.model.Security;
import com.example.testproject.model.TradingHistory;
import com.example.testproject.parser.history_parser.XmlTradeHistoryParser;
import com.example.testproject.repo.TradingHistoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingHistoryService {

    @Value(value = "${trading-history.uri}")
    private String historyURI;
    private final TradingHistoryRepo tradingHistoryRepo;
    private final SecurityService securityService;
    private final XmlTradeHistoryParser parser;

    public SummaryData getSummaryDate(String secId, LocalDate date) {
        TradingHistory history = findBySecIdAndTradeDate(secId, date);
        Security security = securityService.findBySecId(secId);
        return new SummaryData(security, history);
    }

    /**
     * Добавление истории торгов в БД (если история отсутствует, то выполяется запрос к бирже)
     */
    public TradingHistory add(TradingHistory history) {
        Security security = securityService.findBySecId(history.getSecId());
        if (security == null) {

            if (securityService.getSecurityBySecIdFromXml(history.getSecId()) != null) {
                security = securityService.findBySecId(history.getSecId());
                return setSecurityForTradingHistory(history, security);
            }
        } else {
            setSecurityForTradingHistory(history, security);
        }
        return new TradingHistory();
    }

    private TradingHistory setSecurityForTradingHistory(TradingHistory history, Security security) {
        history.setSecurity(security);
        return tradingHistoryRepo.save(history);
    }

    /**
     * Загрузка инструментов в файл "storage/histories_date.xml" с web-site
     */
    public String downloadTradingHistoryXmlByDate(String searchDate) throws MalformedURLException {
        URL website = new URL(historyURI + searchDate);
        String fileName = "storage/histories_" + searchDate + ".xml";

        try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
             FileOutputStream fos = new FileOutputStream(fileName);) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    /**
     * Парсинг XML файла с историями торгов и добавление их в БД
     */
    public void importByXml(String fileName) throws IOException, SAXException {
        List<TradingHistory> histories = parser.parse(fileName);
        histories.forEach(this::add);
    }

    public TradingHistory findBySecIdAndTradeDate(String secId, LocalDate tradeDate) {
        return tradingHistoryRepo.findBySecIdAndTradeDate(secId, tradeDate)
                .orElseThrow(() -> new RuntimeException("Ценная бумага с sec_id = "
                        + secId + " отсутствует"));
    }

    public TradingHistory update(TradingHistoryDto dto) {
        TradingHistory history = findBySecIdAndTradeDate(dto.getSecId(),
                dto.getTradeDate());
        history.setSecId(dto.getSecId());
        history.setTradeDate(dto.getTradeDate());
        history.setNumTrades(dto.getNumTrades());
        history.setOpen(dto.getOpen());
        history.setClose(dto.getClose());

        return tradingHistoryRepo.save(history);
    }

    public void deleteBySecIdAndTradeDate(String secId, LocalDate tradeDate) {
        tradingHistoryRepo.deleteBySecIdAndTradeDate(secId, tradeDate);
    }
}
