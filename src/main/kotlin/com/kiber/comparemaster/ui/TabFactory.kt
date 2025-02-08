package com.kiber.comparemaster.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager
import com.kiber.comparemaster.content.file.EFileTypes

object TabFactory {

    private const val MAX_TAB_NUMBER = 9

    private var counterMap: MutableMap<String, TabContext> = mutableMapOf()


    fun createTab(project: Project, toolWindow: ToolWindow) {
        if (canAddNewTab(toolWindow)) {
            val fileType = resolveFileType()
            val filesPrefix = DefaultEditorsFileManager.createFilePair(fileType)

            counterMap.putIfAbsent(
                project.name, TabContext(
                    tabNumber = 1,
                    timestampAdded = System.currentTimeMillis(),
                    filesPrefix = filesPrefix
                )
            )

            val tabNumber = counterMap[project.name]!!.tabNumber
            val tabName = "TAB-$tabNumber"
            counterMap[project.name]!!.apply {
                this.tabNumber = tabNumber.inc()
                timestampAdded = System.currentTimeMillis()
                this.filesPrefix = filesPrefix
            }

            val panel = ToolWindowPanel(project, filesPrefix)
            panel.name = filesPrefix.toString()

            val content = ContentFactory.getInstance().createContent(panel, tabName, false)
            content.isCloseable = true

            toolWindow.contentManager.addContent(content)
            toolWindow.contentManager.setSelectedContent(content)
        }
    }

    fun releaseTabs(project: Project) {
        counterMap.remove(project.name)
    }


    private fun canAddNewTab(toolWindow: ToolWindow) =
        toolWindow.contentManager.contents
            .map { c -> c.component }
            .filterIsInstance<ToolWindowPanel>()
            .count() < MAX_TAB_NUMBER

    private fun resolveFileType(): EFileTypes =
        if (counterMap.isNotEmpty()) {
            val prefix = counterMap.values.find {
                it.timestampAdded == counterMap.maxOf { entry -> entry.value.timestampAdded }
            }!!.filesPrefix

            DefaultEditorsFileManager.getFilePairType(prefix) ?: EFileTypes.JSON
        } else {
            EFileTypes.JSON
        }

    private data class TabContext(
        var tabNumber: Int,
        var timestampAdded: Long,
        var filesPrefix: Long
    )
}

