package com.mpc.spellcaster.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.ToString

@ToString(includeNames=true, includePackage=false)
@JsonSerialize
class Context implements Serializable {

    String id

    Map<String, Object> contextMap = [:]
    Map<String, Object> variables = [:]

    String toJson() {
        new ObjectMapper().writeValueAsString(contextMap)
    }

    static Context fromJson(String json) {
        new ObjectMapper().readValue(json, Context.class)
    }
}