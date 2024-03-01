package com.kiber.comparemaster.ui

import com.intellij.execution.runToolbar.RunToolbarMoreActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.ActionToolbar
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.action.PluginAction
import javax.swing.SwingConstants

object EditorsToolbarFactory {

    fun createToolbar(actions: List<PluginAction>, targetComponent: ToolWindowPanel): ActionToolbar {
        val group = RunToolbarMoreActionGroup()
        actions.forEach {
            group.add(it)
            group.addSeparator()
        }

        val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.PROJECT_VIEW_TOOLBAR, group, false)
        toolbar.setOrientation(SwingConstants.VERTICAL)
        toolbar.targetComponent = targetComponent

        return toolbar
    }
}