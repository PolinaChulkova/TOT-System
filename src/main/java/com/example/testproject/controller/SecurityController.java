package com.example.testproject.controller;

import com.example.testproject.model.Security;
import com.example.testproject.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import java.io.IOException;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    private final SecurityService securityService;

    /**
     * Загрузка инструмента (ценная бумага) из XML файла
     * @param limit - количество инструментов
     */
    @PostMapping("/download")
    public void downloadSecurities(@RequestParam("limit") String limit) {
        try {
            securityService.importByXml(securityService.downloadSecuritiesFromXml(limit));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Security securityInfo) {
        return ResponseEntity.ok().body(securityService.add(securityInfo));
    }

    @PostMapping("/{securityId}")
    public ResponseEntity<?> findById(@PathVariable Long securityId) {
        return ResponseEntity.ok().body(securityService.findById(securityId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Security securityInfo) {
        return ResponseEntity.ok().body(securityService.update(securityInfo));
    }

    @DeleteMapping("/{securityId}")
    public ResponseEntity<?> delete(@PathVariable Long securityId) {
        securityService.deleteById(securityId);
        return ResponseEntity.ok().body("Security info removed.");
    }
}
