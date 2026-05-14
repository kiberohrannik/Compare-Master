package com.kiber.comparemaster.content.file

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.json.JsonFileType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.PlainTextFileType

enum class EFileTypes(val type: LanguageFileType) {
    ANY(PlainTextFileType.INSTANCE),
    JSON(JsonFileType.INSTANCE),
    XML(XmlFileType.INSTANCE)
}
