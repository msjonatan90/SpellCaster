package com.mpc.spellcaster.model

import org.springframework.data.redis.core.RedisHash

@RedisHash("context")
class Context implements Serializable {

    String id
    HashMap<String, Object> properties

    Context(String id, HashMap<String, Object> properties) {
        this.id = id
        this.properties = properties
    }
}

