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

    override fun update(event: AnActionEvent) {
        val fileType = event.project?.let { project ->
            runCatching { ComponentsResolver.getToolWindowPanel(project).editorFiles.file1.eType }.getOrNull()
        }

        event.presentation.isEnabled = fileType?.let { type -> actions.any { it.supports(type) } } == true
    }

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindowPanel = ComponentsResolver.getToolWindowPanel(getProject(event))
        val fileType = toolWindowPanel.editorFiles.file1.eType

        val group = RunToolbarMoreActionGroup()
        actions.filter { it.supports(fileType) }.forEach {
            group.add(it)
            group.addSeparator()
        }

        if (group.getChildrenCount() == 0) {
            return
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
