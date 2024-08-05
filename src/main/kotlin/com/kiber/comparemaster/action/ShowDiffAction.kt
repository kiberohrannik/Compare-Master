package com.kiber.comparemaster.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.kiber.comparemaster.ui.ComponentsResolver
import com.kiber.comparemaster.ui.IconManager
import com.kiber.comparemaster.ui.diff.SimpleDiffPanel

class ShowDiffAction : PluginAction("Show diff", "", IconManager.showDiff) {

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindowPanel = ComponentsResolver.getToolWindowPanel(getProject(event))

        SimpleDiffPanel.showDiff(getProject(event),
            toolWindowPanel.editorFiles.left(), toolWindowPanel.editorFiles.right())
    }
}