package com.kiber.comparemaster.function

import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.openapi.editor.Document

class HighlightDifferentValuesFunction: FilePairFunction {

    private val jsonMapper =  ObjectMapper()

    override fun apply(left: Document, right: Document) {
        val leftJson = left.text
        val rightJson = right.text

        val leftTree = jsonMapper.readTree(leftJson)
        val rightParser = jsonMapper.readTree(rightJson).traverse()
//        leftTree.fields() {
//            rightParser
//        }

        leftTree.fields().forEachRemaining {
            it ->
        }
    }
}