package com.mpc.spellcaster.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mpc.spellcaster.Context
import org.springframework.expression.Expression
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Service
class SpellCasterService {
    JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost")

    def evaluate(String expression, Context context) {
        try (Jedis jedis = jedisPool.getResource()) {
            // Store the context object in Redis
            jedis.set("context", new ObjectMapper().writeValueAsString(context))

            // Evaluate the expression using SpEL
            final SpelExpressionParser parser = new SpelExpressionParser()
            final Expression exp = parser.parseExpression(expression)
            final Object result = exp.getValue(context)

            // Retrieve the updated context object from Redis
            final Context updatedContext = new ObjectMapper().readValue(jedis.get("context"), Context.class)

            return [result: result, context: updatedContext]
        }
    }
}