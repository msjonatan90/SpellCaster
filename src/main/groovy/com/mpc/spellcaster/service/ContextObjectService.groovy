package com.mpc.spellcaster.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mpc.spellcaster.model.Context
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis

@Service
class ContextObjectService {
    private final Jedis jedis

    private final ObjectMapper objectMapper = new ObjectMapper()

    ContextObjectService() {
        jedis = new Jedis("localhost", 6379)
    }

    def storeContextObject(Context contextObject) {
        final String jsonString = objectMapper.writeValueAsString(contextObject)
        jedis.set(contextObject.id, jsonString)
    }

    def getContextObject(String id) {
        def jsonString = jedis.get(id)
        objectMapper.readValue(jsonString, Map)
    }
}