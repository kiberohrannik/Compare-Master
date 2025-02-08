package com.kiber.comparemaster.content.file

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.json.JsonFileType
import com.intellij.openapi.fileTypes.LanguageFileType

enum class EFileTypes(val type: LanguageFileType) {
    JSON(JsonFileType.INSTANCE),
    XML(XmlFileType.INSTANCE)
}