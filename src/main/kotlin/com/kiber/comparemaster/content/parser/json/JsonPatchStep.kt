package com.kiber.comparemaster.content.parser.json

data class JsonPatchStep(var op: String, var path: String, var value: String) {
    constructor(): this("", "", "")
}