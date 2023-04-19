package com.mpc.spellcaster

import com.fasterxml.jackson.databind.ObjectMapper

class Context implements Serializable {
    Map<String, Object> contextMap = [:]

    String toJson() {
        new ObjectMapper().writeValueAsString(contextMap)
    }

    static Context fromJson(String json) {
        new ObjectMapper().readValue(json, Context.class)
    }
}