package com.kiber.comparemaster.content.file

import com.intellij.json.JsonFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.content.file.EVirtualFile

class JsonEVirtualFile private constructor(internalFile: VirtualFile) : EVirtualFile(EFileTypes.JSON, internalFile) {

    internal constructor(fileName: String) : this(
        LightVirtualFile(fileName, JsonFileType.INSTANCE, "")
    )
}