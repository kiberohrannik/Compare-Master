package com.kiber.comparemaster.action.filetypes

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindow
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.content.file.EFileTypes
import javax.swing.Icon

abstract class ChangeFileTypeAction(
    hint: String,
    icon: Icon,
    protected val type2Change: EFileTypes,
    protected val toolWindow: ToolWindow
) :
    PluginAction(hint, "", icon) {

    override fun actionPerformed(event: AnActionEvent) {
        FileTypeSwitcher.apply(toolWindow, type2Change, getProject(event))
    }
}
