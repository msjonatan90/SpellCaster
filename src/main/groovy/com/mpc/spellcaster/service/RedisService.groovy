package com.mpc.spellcaster.service

import com.mpc.spellcaster.model.Context
import redis.clients.jedis.Jedis
import com.fasterxml.jackson.databind.ObjectMapper

class RedisService {
    Jedis jedis
    ObjectMapper objectMapper

    RedisService(String host, int port) {
        jedis = new Jedis(host, port)
        objectMapper = new ObjectMapper()
    }

    String storeContext(Context context) {
        String key = UUID.randomUUID().toString()
        String contextJson = objectMapper.writeValueAsString(context)
        jedis.set(key, contextJson)
        return key
    }

    Context getContext(String key) {
        String contextJson = jedis.get(key)
        return objectMapper.readValue(contextJson, Context.class)
    }
}

