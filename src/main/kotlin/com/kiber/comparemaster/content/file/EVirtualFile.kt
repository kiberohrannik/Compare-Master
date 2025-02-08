package com.kiber.comparemaster.content.file

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile

abstract class EVirtualFile(var eType: EFileTypes, var internalFile: VirtualFile): LightVirtualFile() {

}