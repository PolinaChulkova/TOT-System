package com.example.testproject.repo;

import com.example.testproject.model.TradingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TradingHistoryRepo extends JpaRepository<TradingHistory, Long> {
    Optional<TradingHistory> findBySecIdAndTradeDate(String secId, LocalDate tradeDate);

    void deleteBySecIdAndTradeDate(String secId, LocalDate tradeDate);
}
