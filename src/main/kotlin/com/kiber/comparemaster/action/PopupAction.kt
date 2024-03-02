package com.kiber.comparemaster.action

import com.intellij.execution.runToolbar.RunToolbarMoreActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.impl.ActionButton
import com.kiber.comparemaster.ui.ComponentsResolver
import java.awt.event.MouseEvent
import javax.swing.Icon

class PopupAction(
    hint: String,
    description: String = "",
    icon: Icon,
    private val actions: List<FilePairAction> = listOf()
) : PluginAction(hint, description, icon) {

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindowPanel = ComponentsResolver.getToolWindowPanel(getProject(event))

        val group = RunToolbarMoreActionGroup()
        actions.forEach {
            group.add(it)
            group.addSeparator()
        }

        val popupMenu = ActionManager.getInstance()
            .createActionPopupMenu(ActionPlaces.TOOLWINDOW_POPUP, group)
        popupMenu.setTargetComponent(toolWindowPanel)

        val mouseEvent = event.inputEvent as? MouseEvent

        if (mouseEvent != null) {
            popupMenu.component.show(toolWindowPanel,mouseEvent.x + 20, (mouseEvent.source as ActionButton).y)
        }
    }
}