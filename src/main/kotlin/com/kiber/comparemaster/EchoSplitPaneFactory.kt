package com.kiber.comparemaster

import com.intellij.util.ui.JBUI
import javax.swing.JPanel
import javax.swing.JSplitPane

class EchoSplitPaneFactory: SplitPaneFactory() {

    override fun createSplitPane(leftPanel: JPanel, rightPanel: JPanel): JSplitPane {
        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerSize(1);
        splitPane.border = JBUI.Borders.empty()
        return  splitPane
    }
}