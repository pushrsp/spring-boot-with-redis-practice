package com.example.helloworld.cache.service;

import com.example.helloworld.cache.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private final ExternalApiService externalApiService;
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public UserService(ExternalApiService externalApiService, StringRedisTemplate stringRedisTemplate) {
        this.externalApiService = externalApiService;
        this.redisTemplate = stringRedisTemplate;
    }

    public UserProfile getUserProfile(String userId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cacheName = ops.get("nameKey: " + userId);

        String username = null;
        if(!Objects.isNull(cacheName)) {
            username = cacheName;
        } else {
            username = externalApiService.getName(userId);
            ops.set("nameKey: " + userId, username, 5, TimeUnit.SECONDS);
        }


        int age = externalApiService.getAge(userId);

        return new UserProfile(username, age);
    }
}
