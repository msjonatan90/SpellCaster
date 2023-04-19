package com.mpc.spellcaster.service

import redis.clients.jedis.Jedis
import com.fasterxml.jackson.databind.ObjectMapper

class RedisService {
    Jedis jedis
    ObjectMapper objectMapper

    RedisService(String host, int port) {
        jedis = new Jedis(host, port)
        objectMapper = new ObjectMapper()
    }

    void storeContext(String key, Object context) {
        String contextJson = objectMapper.writeValueAsString(context)
        jedis.set(key, contextJson)
    }

    Object getContext(String key, Class<Object> contextClass) {
        String contextJson = jedis.get(key)
        return objectMapper.readValue(contextJson, contextClass)
    }
}