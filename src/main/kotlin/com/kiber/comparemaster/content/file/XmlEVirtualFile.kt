package com.kiber.comparemaster.content.file

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile

class XmlEVirtualFile private constructor(internalFile: VirtualFile) : EVirtualFile(EFileTypes.XML, internalFile) {

    val attrs: JsonAttributes = JsonAttributes()

    internal constructor(fileName: String) : this(
        LightVirtualFile(fileName, XmlFileType.INSTANCE, "")
    )
}