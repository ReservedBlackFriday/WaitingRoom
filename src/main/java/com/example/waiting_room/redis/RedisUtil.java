package com.example.waiting_room.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, String> redisTemplate;

    public void setData(String key, String value){
        Duration expireDuration = Duration.ofSeconds(30L);
        redisTemplate.opsForValue().set(key, value, expireDuration);
    }

    public String getData(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean existData(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public String zPop(String key){
        return redisTemplate.opsForZSet().popMin(key).getValue();
    }


    public Boolean zAdd(String key, String value, int score){
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long zCard(String str){
        return redisTemplate.opsForZSet().size(str);
    }

    public Long zDelete(String key, String value){
        return redisTemplate.opsForZSet().remove(key, value);
    }

    public Long zRank(String key, String value){
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
