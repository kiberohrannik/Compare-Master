package com.kiber.comparemaster.content.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.openapi.util.TextRange

class JsonDiffSearchEngine {

    private val jsonMapper = ObjectMapper()

    //TODO don't forget to trim json text
    fun findDifferences(leftText: String, rightText: String): Set<TextRange> {

        val patchOperations = JsonPatchOperations.toJsonPatch(leftText, rightText)


        return setOf()
    }

    private fun findTextRange(op: JsonPatchStep) : TextRange {
        TODO( "Not implmented yet" )
    }
}