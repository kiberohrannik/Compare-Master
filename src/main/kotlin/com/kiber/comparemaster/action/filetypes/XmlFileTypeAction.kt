package com.kiber.comparemaster.action.filetypes

import com.intellij.icons.AllIcons
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindow
import com.intellij.testFramework.LightVirtualFile
import com.intellij.ui.JBSplitter
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.ui.DefaultEditorManager
import javax.swing.JPanel

class XmlFileTypeAction(private val toolWindow: ToolWindow) :
    PluginAction("Use XML format", "", AllIcons.FileTypes.Xml) {

    override fun actionPerformed(event: AnActionEvent) {
        val selectedTab = toolWindow.contentManager.selectedContent

        if (selectedTab != null) {
            if (selectedTab.component is ToolWindowPanel) {
                val filesPrefix = selectedTab.component.name.toLong()
                DefaultEditorsFileManager.changeFileType(filesPrefix, EFileTypes.XML, getProject(event))

                val jpanel = (selectedTab.component.components.find { it is JBSplitter } as JPanel)

                val leftEditor = jpanel.components[1] as JPanel
                val rightEditor = jpanel.components[2] as JPanel

                val newLeftEditor = DefaultEditorManager(getProject(event)).createEditor(
                    DefaultEditorsFileManager.getFilePair(filesPrefix)!!.left()
                )

                val newRightEditor = DefaultEditorManager(getProject(event)).createEditor(
                    DefaultEditorsFileManager.getFilePair(filesPrefix)!!.right()
                )

                jpanel.remove(leftEditor)
                jpanel.remove(rightEditor)

                jpanel.add(newLeftEditor.component)
                jpanel.add(newRightEditor.component)

                jpanel.revalidate()
                jpanel.repaint()
            }
        }
    }
}