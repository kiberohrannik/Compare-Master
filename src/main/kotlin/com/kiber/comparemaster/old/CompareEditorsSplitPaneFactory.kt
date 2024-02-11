package com.kiber.comparemaster.old

import javax.swing.JPanel
import javax.swing.JSplitPane

class CompareEditorsSplitPaneFactory: SplitPaneFactory {

    override fun createSplitPane(leftPanel: JPanel, rightPanel: JPanel): JSplitPane {
        return JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
    }
}