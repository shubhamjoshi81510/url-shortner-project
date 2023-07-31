package com.urlshort.service;

import com.urlshort.entity.UrlMapping;
import com.urlshort.model.UrlReq;
import com.urlshort.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
public class UrlService {
    @Autowired
    private UrlRepository urlrepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }


    public UrlMapping genrateUrl(UrlReq urlReq) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setUrl(urlReq.getUrl());
        urlMapping.setUrlKey(generateRandomString(10));
        urlrepository.save(urlMapping);
        return urlMapping;
    }

    public UrlMapping getUrlByKey(String key) {
        Optional<UrlMapping> urlData = urlrepository.findById(key);
        if (urlData.isEmpty()) {
            throw new IllegalArgumentException("Key not found");
        }
       return urlData.get();
    }
}



