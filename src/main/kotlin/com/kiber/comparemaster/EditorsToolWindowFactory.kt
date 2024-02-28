package com.kiber.comparemaster

import com.intellij.execution.runners.RunTab
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.old.AskQuestionAction

class EditorsToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = EditorsPanel(project)
        val content = ContentFactory.getInstance().createContent(panel, "Tab 1", false)
        toolWindow.contentManager.addContent(content)

        //***********************************
        //*****************************k******

        val group = DefaultActionGroup()
        group.add(AskQuestionAction())
        group.addSeparator()
        group.add(AskQuestionAction())

        val outerGroup = RunTab.ToolbarActionGroup(group)
        val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.EDITOR_TAB, outerGroup, true)
        toolbar.targetComponent = toolWindow.component

        val toolbarContent = ContentFactory.getInstance().createContent(toolbar.component, "Tab 2", false)
        toolWindow.contentManager.addContent(toolbarContent)

    }
}