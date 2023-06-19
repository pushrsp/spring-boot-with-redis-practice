package com.example.helloworld.cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {
    public String getName(String userId) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("getName");

        if(userId.equals("A")) {
            return "Adam";
        }

        if(userId.equals("B")) {
            return "Bob";
        }

        return "";
    }

    @Cacheable(cacheNames = "userAgeCache", key = "#userId")
    public int getAge(String userId) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("getAge");

        if(userId.equals("A")) {
            return 28;
        }

        if(userId.equals("B")) {
            return 32;
        }

        return 0;
    }
}
