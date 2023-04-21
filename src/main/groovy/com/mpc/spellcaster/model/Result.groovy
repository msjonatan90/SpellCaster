package com.mpc.spellcaster.model

class Result {
    String message
    String status
    Object data

    Result(String message, String status, Object data) {
        this.message = message
        this.status = status
        this.data = data
    }
}
