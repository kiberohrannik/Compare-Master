package com.kiber.comparemaster.function

import com.kiber.comparemaster.content.file.EFileTypes

interface JsonFilePairFunction: FilePairFunction {

    override fun supports(fileType: EFileTypes): Boolean {
        return fileType == EFileTypes.JSON
    }
}