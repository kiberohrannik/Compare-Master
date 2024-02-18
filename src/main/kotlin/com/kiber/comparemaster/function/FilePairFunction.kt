package com.kiber.comparemaster.function

import com.intellij.openapi.editor.Document

fun interface FilePairFunction {

    fun apply(left: Document, right: Document): Unit
}