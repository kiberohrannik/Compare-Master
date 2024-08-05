package com.kiber.comparemaster.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.kiber.comparemaster.ui.ComponentsResolver

class CreateNewTabAction : PluginAction("Create new tab", "", AllIcons.Actions.NewFolder) {

    override fun actionPerformed(event: AnActionEvent) {
        ComponentsResolver.addToolWindowTab(getProject(event))
    }
}