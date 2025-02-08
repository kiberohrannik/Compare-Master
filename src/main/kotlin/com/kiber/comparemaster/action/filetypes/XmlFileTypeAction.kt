package com.kiber.comparemaster.action.filetypes

import com.intellij.icons.AllIcons
import com.intellij.openapi.wm.ToolWindow
import com.kiber.comparemaster.content.file.EFileTypes

class XmlFileTypeAction(toolWindow: ToolWindow) :
    ChangeFileTypeAction(
        hint = "Use XML format",
        icon = AllIcons.FileTypes.Xml,
        type2Change = EFileTypes.XML,
        toolWindow
    )