package com.example.testproject.dto;

import com.example.testproject.model.Security;
import com.example.testproject.model.TradingHistory;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Класс для получения сводных данных
 */
@Getter
public class SummaryData {
    private final String secId;
    private final String regNumber;
    private final String name;
    private final String emitentTitle;
    private final LocalDate tradeDate;
    private final Long numTrades;
    private final Double open;
    private final Double close;

    public SummaryData(Security security, TradingHistory tradingHistory) {
        this.secId = security.getSecId();
        this.regNumber = security.getRegNumber();
        this.name = security.getName();
        this.emitentTitle = security.getEmitentTitle();
        this.tradeDate = tradingHistory.getTradeDate();
        this.numTrades = tradingHistory.getNumTrades();
        this.open = tradingHistory.getOpen();
        this.close = tradingHistory.getOpen();
    }
}
