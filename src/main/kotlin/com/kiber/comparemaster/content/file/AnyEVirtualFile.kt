package com.kiber.comparemaster.content.file

import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile

class AnyEVirtualFile private constructor(internalFile: VirtualFile) : EVirtualFile(EFileTypes.ANY, internalFile) {

    internal constructor(fileName: String) : this(
        LightVirtualFile(fileName, PlainTextFileType.INSTANCE, "")
    )
}
