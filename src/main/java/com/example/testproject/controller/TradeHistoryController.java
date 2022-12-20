package com.example.testproject.controller;

import com.example.testproject.dto.TradingHistoryDto;
import com.example.testproject.model.TradingHistory;
import com.example.testproject.service.TradingHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/trading-history")
@RequiredArgsConstructor
@Slf4j
public class TradeHistoryController {

    private final TradingHistoryService tradingHistoryService;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummaryDate(@RequestParam("secId") String secId,
                                            @RequestParam("date") String date) {
        return ResponseEntity.ok().body(tradingHistoryService.getSummaryDate(secId, LocalDate.parse(date)));
    }

    /**
     * Загрузка истории торгов по дате из XML файла
     */
    @PostMapping("/download")
    public void downloadTradingHistories(@RequestParam("date") String date) {
        try {
            tradingHistoryService.importByXml(tradingHistoryService.downloadTradingHistoryXmlByDate(date));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TradingHistoryDto dto) {
        return ResponseEntity.ok().body(tradingHistoryService.add(new TradingHistory(dto)));
    }

    @PostMapping("/{secId}/{date}")
    public ResponseEntity<?> findBySecIdAndTradeDate(@PathVariable String secId,
                                         @PathVariable LocalDate date) {
        return ResponseEntity.ok().body(tradingHistoryService.findBySecIdAndTradeDate(secId, date));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody TradingHistoryDto dto) {
        return ResponseEntity.ok().body(tradingHistoryService.update(dto));
    }

    @DeleteMapping("/{secId}/{date}")
    public ResponseEntity<?> deleteById(@PathVariable String secId,
                                    @PathVariable LocalDate date) {
        tradingHistoryService.deleteBySecIdAndTradeDate(secId, date);
        return ResponseEntity.ok().body("Trading history removed.");
    }
}
