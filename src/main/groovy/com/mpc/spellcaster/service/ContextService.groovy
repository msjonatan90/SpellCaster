package com.mpc.spellcaster.service

import com.mpc.spellcaster.model.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentHashMap

@Service
class ContextService {

    @Autowired
    private RedisService redisService

    private Map<String, Context> cache = new ConcurrentHashMap<>()

    void put(String key, Context value) {
        redisService.storeContext(value)
        cache.put(key, value)
    }

    Context get(String key) {
        Context value = cache.get(key)
        if (value == null) {
            value = redisService.getContext(key)
            if (value != null) {
                cache.put(key, value)
            }
        }
        return value
    }

    void delete(String key) {
        redisService.deleteContext(key)
        cache.remove(key)
    }

}