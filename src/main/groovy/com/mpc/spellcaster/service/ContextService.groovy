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
        redisService.saveContext(key, value)
        cache.put(key, value)
    }

    Context get(String key) {
        Context context = cache.get(key)
        if (context == null) {
            context = redisService.getContext(key)
            if (context != null) {
                cache.put(key, context)
            }
        }
        return context
    }

    void delete(String key) {
        redisService.deleteContext(key)
        cache.remove(key)
    }

}