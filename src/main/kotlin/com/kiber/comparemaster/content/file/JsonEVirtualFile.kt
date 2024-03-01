package com.kiber.comparemaster.content.file

import com.intellij.json.JsonFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile

class JsonEVirtualFile private constructor(internalFile: VirtualFile) : EVirtualFile(EFileTypes.JSON, internalFile) {

    val attrs: JsonAttributes = JsonAttributes()

    internal constructor(fileName: String) : this(
        LightVirtualFile(fileName, JsonFileType.INSTANCE, "")
    )
}