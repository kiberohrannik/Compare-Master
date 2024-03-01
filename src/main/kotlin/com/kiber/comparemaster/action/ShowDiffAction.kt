package com.kiber.comparemaster.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.kiber.comparemaster.ui.diff.SimpleDiffPanel

class ShowDiffAction : PluginAction("Show diff", "", AllIcons.Actions.Diff) {

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindowPanel = getToolWindowPanel(event)

        SimpleDiffPanel.showDiff(getProject(event),
            toolWindowPanel.editorFiles.left(), toolWindowPanel.editorFiles.right())
    }
}