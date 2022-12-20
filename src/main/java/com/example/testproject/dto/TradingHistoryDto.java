package com.example.testproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
public class TradingHistoryDto {
    private final String secId;
    private final LocalDate tradeDate;
    private final Long numTrades;
    private final Double open;
    private final Double close;
}
