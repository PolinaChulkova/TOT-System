package com.example.testproject.model;

import com.example.testproject.dto.TradingHistoryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trading_histories")
@IdClass(TradeHistoryPK.class)
@NoArgsConstructor
@Getter
@Setter
public class TradingHistory {
    @Id
    @Column(name = "sec_id")
    private String secId;
    @Id
    @Column(name = "tarde_date")
    private LocalDate tradeDate;
    @Column(name = "num_trades")
    private Long numTrades;
    @Column(name = "open")
    private Double open;

    @ManyToOne
    @JoinColumn(name = "sec_id", referencedColumnName = "sec_id",
            insertable = false, updatable = false)
    private Security security;

    public TradingHistory(TradingHistoryDto dto) {
        this.secId = dto.getSecId();
        this.tradeDate = dto.getTradeDate();
        this.numTrades = dto.getNumTrades();
        this.open = dto.getOpen();
    }

    public TradingHistory(String secId, LocalDate tradeDate, Long numTrades, Double open, Security security) {
        this.secId = secId;
        this.tradeDate = tradeDate;
        this.numTrades = numTrades;
        this.open = open;
        this.security = security;
    }
}
