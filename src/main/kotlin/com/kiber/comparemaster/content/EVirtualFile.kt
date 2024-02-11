package com.kiber.comparemaster.content

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile

abstract class EVirtualFile(val eType: EFileTypes, val internalFile: VirtualFile): LightVirtualFile() {

}