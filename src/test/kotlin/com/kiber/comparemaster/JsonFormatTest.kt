package com.kiber.comparemaster

import com.fasterxml.jackson.databind.ObjectMapper

fun main() {
    //        println(Language.getRegisteredLanguages())
//        val psiFile = PsiFileFactoryImpl(project)
//            .createFileFromText("leftJson", JsonLanguage.INSTANCE, "{\"elem1\":\"this is JSON\" }")
//        println(psiFile.fileType)

//        val leftEditor = EditorFactory.getInstance()
//            .createEditor(DocumentImpl("{\"elem1\":\"this is JSON\" }"), project, psiFile.virtualFile, false)


    val prettyJson = """{"menu": {
  "id": "file",
  "value": "File",
  "popup": {
    "menuitem": [
      {"value": "New", "onclick": "CreateNewDoc()"},
      {"value": "Open", "onclick": "OpenDoc()"},
      {"value": "Close", "onclick": "CloseDoc()"}
    ]
  }
}}"""

    val rawJson = ObjectMapper().readTree(prettyJson).toString()

    println("rawJson = ${rawJson}")
}