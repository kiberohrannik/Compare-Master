package com.kiber.comparemaster.action.filetypes

import com.intellij.icons.AllIcons
import com.intellij.openapi.wm.ToolWindow
import com.kiber.comparemaster.content.file.EFileTypes

class JsonFileTypeAction(toolWindow: ToolWindow) :
    ChangeFileTypeAction(
        hint = "Use JSON format",
        icon = AllIcons.FileTypes.Json,
        type2Change = EFileTypes.JSON,
        toolWindow
    )