package com.kiber.comparemaster.content.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.flipkart.zjsonpatch.JsonDiff
import com.intellij.openapi.util.TextRange

class JsonDiffSearchEngine {

    private val jsonMapper = ObjectMapper()

    //TODO don't forget to trim json text
    fun findDifferences(leftText: String, rightText: String): Set<TextRange> {

        val patchOperations = JsonPatchMapper.toJsonPatch(leftText, rightText)


        return setOf()
    }

    private fun findTextRange(op: JsonPatchOperation) : TextRange {
        TODO( "Not implmented yet" )
    }
}