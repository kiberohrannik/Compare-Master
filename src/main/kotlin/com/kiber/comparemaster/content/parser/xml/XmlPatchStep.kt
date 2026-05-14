package com.kiber.comparemaster.content.parser.xml

data class XmlPatchStep(
    val op: String,
    val path: String,
    val value: String? = null,
    val parentPath: String? = null,
    val nodeXml: String? = null,
    val nodeName: String? = null,
    val nodeIndex: Int? = null,
)
