package com.kiber.comparemaster.content.parser

data class JsonPatchOperation(var op: String, var path: String, var value: String) {
    constructor(): this("", "", "")
}