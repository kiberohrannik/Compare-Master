package com.kiber.comparemaster

import com.intellij.analysis.problemsView.toolWindow.ProblemsViewPanel
import com.intellij.analysis.problemsView.toolWindow.ProblemsViewState
import com.intellij.execution.runToolbar.RunToolbarMoreActionGroup
import com.intellij.execution.runners.RunTab
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.JBTabsPaneImpl
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.tabs.JBTabsFactory
import com.intellij.ui.tabs.impl.JBTabsImpl
import com.kiber.comparemaster.old.AskQuestionAction
import javax.swing.SwingConstants


class EditorsToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = EditorsPanel(project)
        val content = ContentFactory.getInstance().createContent(panel, "Tab 1", false)

        //***********************************
        //*****************************k******


//        val group = DefaultActionGroup()
        val group = RunToolbarMoreActionGroup()

        group.add(AskQuestionAction())
        group.addSeparator()
        group.add(AskQuestionAction())

//        AllIcons.Actions.Collapseall

        val outerGroup = RunTab.ToolbarActionGroup(group)


        val smth = JBTabsFactory.createTabs(project)
        (smth as JBTabsImpl).setSideComponentVertical(true)
        val outputTabs = JBTabsPaneImpl(project, SwingConstants.TOP, toolWindow.disposable)


//        val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.EDITOR_TAB, outerGroup, true)
//        toolbar.targetComponent = panel

//        val toolbarContent = ContentFactory.getInstance().createContent(toolbar.component, "Tab 2", false)
//        toolWindow.contentManager.addContent(toolbarContent)

//        toolWindow.setAdditionalGearActions(group)
        toolWindow.setTitleActions(listOf(AskQuestionAction(), AskQuestionAction()))

        val example = ProblemsViewPanel(project, "12314", ProblemsViewState.getInstance(project)) { -> "123" }


        toolWindow.contentManager.addContent(ContentFactory.getInstance().createContent(example, "23444", false))
//        panel.add(toolbar.component)

        //*************************************************

//        toolWindow.setAdditionalGearActions(group) // --add options to 'gear' menu


        toolWindow.contentManager.addContent(content)
    }
}