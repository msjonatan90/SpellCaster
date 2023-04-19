package com.mpc.spellcaster.service

import com.mpc.spellcaster.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SpellCasterService {
    @Autowired
    RedisTemplate<String, Context> redisTemplate

    // ...
}