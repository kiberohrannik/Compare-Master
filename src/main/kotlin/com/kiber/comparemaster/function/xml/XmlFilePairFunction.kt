package com.kiber.comparemaster.function.xml

import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.function.FilePairFunction

interface XmlFilePairFunction: FilePairFunction {

    override fun supports(fileType: EFileTypes): Boolean {
        return fileType == EFileTypes.XML
    }
}