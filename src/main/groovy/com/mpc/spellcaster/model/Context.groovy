package com.mpc.spellcaster.model

import org.springframework.data.redis.core.RedisHash

@RedisHash("Context")
class Context extends Expando implements Serializable {
}
