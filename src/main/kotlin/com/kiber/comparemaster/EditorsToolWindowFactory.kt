package com.kiber.comparemaster

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


        //************************************************************
        toolWindow.setTitleActions(listOf(AskQuestionAction(), AskQuestionAction()))

//        val example = ProblemsViewPanel(project, "12314", ProblemsViewState.getInstance(project)) { -> "123" }
//        toolWindow.contentManager.addContent(ContentFactory.getInstance().createContent(example, "23444", false))
        // ************************************************************
    }
}