package com.example.testproject.service;

import com.example.testproject.model.Security;
import com.example.testproject.parser.security_parser.XmlSecurityParser;
import com.example.testproject.repo.SecurityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    @Value(value = "${securities.uri}")
    private String securityURI;
    private final SecurityRepo securityRepo;
    private final XmlSecurityParser parser;

    /**
     * Загрузка инструментов в файл "storage/securities.xml" с web-site
     */
    public String downloadSecuritiesFromXml(String limit) throws MalformedURLException {
        URL website = new URL(securityURI + "limit=" + limit);
        String fileName = "storage/securities.xml";

        try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
             FileOutputStream fos = new FileOutputStream(fileName);) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    /**
     * Парсинг XML файла с набором ценных бумаг и добавление их в БД
     */
    public List<Security> importByXml(String file) throws IOException, SAXException {
        List<Security> securities = parser.parse(file);
        securities.forEach(this::add);
        return securities;
    }

    /**
     * Получение ценной бумаги по sec_id с web-site
     */
    public Security getSecurityBySecIdFromXml(String secId) {
        Security security = null;
        URL website = null;
        InputStream inputStream = null;
        try {
            website = new URL(securityURI + "q=" + secId + "&limit=1");
            inputStream = website.openStream();
            security = parser.parse(inputStream);
            if (security.getSecId().equals(secId)) {
                return add(security);
            }
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Security findById(Long id) {
        return securityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Security information by id="
                        + id + "not found"));
    }

    public Security findBySecId(String secId) {
        return securityRepo.findBySecId(secId);
    }

    public Security add(Security security) {
        return securityRepo.save(security);
    }

    public Security update(Security security) {
        Security sec = findById(security.getId());
        sec.setSecId(security.getSecId());
        sec.setRegNumber(security.getRegNumber());
        sec.setName(security.getName());
        sec.setEmitentTitle(security.getEmitentTitle());

        return securityRepo.save(sec);
    }

    public void deleteById(Long id) {
        securityRepo.deleteById(id);
    }

}
