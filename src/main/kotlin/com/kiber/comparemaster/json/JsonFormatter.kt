package com.kiber.comparemaster.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.jetbrains.rd.util.string.printToString

class JsonFormatter {
    private val objectMapper = ObjectMapper()

    fun toPrettyJson(json: String): String {
        return objectMapper.readTree(json).toPrettyString()
    }

    fun toRawJson(json: String): String {
        return objectMapper.readTree(json).toString()
    }
}