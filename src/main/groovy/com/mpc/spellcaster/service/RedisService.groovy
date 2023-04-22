package com.mpc.spellcaster.service

import com.mpc.spellcaster.error.ContextNotFoundException
import org.springframework.dao.DataAccessException
import org.springframework.data.redis.connection.RedisConnection
import org.springframework.data.redis.connection.RedisScriptingCommands
import org.springframework.data.redis.connection.ReturnType
import org.springframework.data.redis.core.RedisCallback
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Service

@Service
class RedisService {

    private final RedisTemplate<String, Object> redisTemplate //use reactive Redis instead

    RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate
    }

    void saveContext(String appName, String contextKey, Object contextPayload) {
        //Save the context to Redis
        redisTemplate.opsForHash().put(appName, contextKey, contextPayload)
    }

    Object getContext(String appName, String contextKey) {
        // Get the context from Redis
        final Object context = redisTemplate.opsForHash().get(appName, contextKey)
        if (context == null) {
            throw new ContextNotFoundException("Context not found for contextKey: $contextKey")
        }
        return context
    }

    void deleteContext(String appName, String key) {
        // Delete the context from Redis
        redisTemplate.opsForHash().delete(appName, key)
    }

    def findByExpression(String appName, String contextKey, String spelExpression) {

        String spelExpressionAST = new SpelExpressionParser().parseRaw(spelExpression).toStringAST()
                .replaceAll("'","\\\\'")

        String script = "local context = redis.call('HGET', '${appName}', '${contextKey}');\n" +
                "return redis.call('EVAL', '${spelExpressionAST}', 0, context)"

        print(script)

        return redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisScriptingCommands scriptingCommands = connection.scriptingCommands()
                return scriptingCommands.eval(script.getBytes(), ReturnType.MULTI, 0)
            }
        })
    }
}