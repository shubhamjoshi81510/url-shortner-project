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

    @Autowired
    private RedisService redisService;

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


    public UrlMapping generateUrl(UrlReq urlReq) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setUrl(urlReq.getUrl());
        urlMapping.setUrlKey(generateRandomString(10));
        urlrepository.save(urlMapping);
        return urlMapping;
    }

    public String getUrlByKey(String key) {

        System.out.println("Fetching URL Data For key: "+ key);

        // Step-1 First Check the availability in redis cache for the key.
        String urlFromRedis= redisService.getFromRedis(key);

        // Step2: If URL Mapping is found in redis cache, then return same.
        if(urlFromRedis!= null) {
            System.out.println("Key: "+ key + " With Value: "+ urlFromRedis + " found in redis.");
            return urlFromRedis;
        }
        System.out.println("Key: "+ key + " not found in redis, Fetching From DB");

        // Step3: If Not Found on redis, then try to fetch from persistence.
        Optional<UrlMapping> urlDataOptional = urlrepository.findById(key);

        // Step4: If Mapping Not Found in persistence, then throw an exception.
        if (urlDataOptional.isEmpty()) {
            System.out.println("Key: "+ key + " not found in database as well");
            throw new IllegalArgumentException("Key not found");
        }

        UrlMapping urlData = urlDataOptional.get();

        // Step5: Insert the Value in redis, so from next time we don't have to call persistence for this.
        redisService.put(key, urlData.getUrl());
        System.out.println("Key: "+ key + " value inserted in redis successfully.");

        // Step6: Return the URL
       return urlData.getUrl();
    }
}



