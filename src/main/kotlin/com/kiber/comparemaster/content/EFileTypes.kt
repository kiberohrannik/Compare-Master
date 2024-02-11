package com.kiber.comparemaster.content

import com.intellij.json.JsonFileType
import com.intellij.openapi.fileTypes.LanguageFileType

enum class EFileTypes(type: LanguageFileType) {
    JSON(JsonFileType.INSTANCE)
}