package com.kiber.comparemaster

import com.intellij.util.ui.JBUI
import javax.swing.JPanel
import javax.swing.JSplitPane

abstract class SplitPaneFactory {

    abstract fun createSplitPane(leftPanel: JPanel, rightPanel: JPanel): JSplitPane
}