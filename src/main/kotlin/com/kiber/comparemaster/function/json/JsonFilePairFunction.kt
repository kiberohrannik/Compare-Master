package com.kiber.comparemaster.function.json

import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.function.FilePairFunction

interface JsonFilePairFunction: FilePairFunction {

    override fun supports(fileType: EFileTypes): Boolean {
        return fileType == EFileTypes.JSON
    }
}