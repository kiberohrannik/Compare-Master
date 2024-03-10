package com.kiber.comparemaster.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.util.*

object JsonFormatter {
    private val jsonMapper = ObjectMapper()

    init {
        jsonMapper.setConfig(jsonMapper.serializationConfig.without(SerializationFeature.INDENT_OUTPUT))
    }

    fun toPrettyJson(json: String): String = if (json.isBlank()) json else jsonMapper.readTree(json).toPrettyString()
    fun toRawJson(json: String): String = if (json.isBlank()) json else jsonMapper.readTree(json).toString()

    fun toSortedJson(json: String): String {
        if (json.isBlank()) {
            return json
        }

        jsonMapper.setConfig(jsonMapper.serializationConfig.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY))
        jsonMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)

        val jsonNode = jsonMapper.readTree(json)

        val sortedJsonMap = jsonMapper.convertValue(
            jsonNode,
            object : TypeReference<TreeMap<String, Any>>() {}
        ).toSortedMap()

        val sortedNode = jsonMapper.convertValue(sortedJsonMap, object : TypeReference<JsonNode>() {})

        return jsonMapper.writeValueAsString(sortedNode)
    }
}