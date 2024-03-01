package com.kiber.comparemaster.json

import com.fasterxml.jackson.databind.ObjectMapper

object JsonFormatter {
    private val objectMapper = ObjectMapper()

    fun toPrettyJson(json: String): String {
        return objectMapper.readTree(json).toPrettyString()
    }

    fun toRawJson(json: String): String {
        return objectMapper.readTree(json).toString()
    }
}