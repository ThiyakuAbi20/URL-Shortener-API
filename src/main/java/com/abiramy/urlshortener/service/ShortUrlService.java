package com.abiramy.urlshortener.service;

import com.abiramy.urlshortener.repository.ShortUrlRepository;
import java.util.Random;

public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 6;


    public ShortUrlService (ShortUrlRepository shortUrlRepository){
        this.shortUrlRepository = shortUrlRepository;
    }

    private String generateShortCode(){

        Random random = new Random();

        StringBuilder shortCode = new StringBuilder();

        for(int i = 0; i < SHORT_CODE_LENGTH; i++){

            int randomIndex = random.nextInt(CHARACTERS.length());

            shortCode.append(
                    CHARACTERS.charAt(randomIndex)
            );
        }

        return shortCode.toString();
    }


}
