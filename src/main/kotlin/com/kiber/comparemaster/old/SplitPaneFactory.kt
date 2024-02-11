package com.kiber.comparemaster.old

import com.intellij.util.ui.JBUI
import javax.swing.JPanel
import javax.swing.JSplitPane

fun interface SplitPaneFactory {

    fun createSplitPane(leftPanel: JPanel, rightPanel: JPanel): JSplitPane
}