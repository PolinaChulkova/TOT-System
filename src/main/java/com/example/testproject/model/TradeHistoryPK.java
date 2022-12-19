package com.example.testproject.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
public class TradeHistoryPK implements Serializable {
    private String secId;
    private LocalDate tradeDate;
}
