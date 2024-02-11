package com.kiber.comparemaster.old

import com.kiber.comparemaster.ui.addEditors
import com.kiber.comparemaster.ui.customize
import javax.swing.JPanel
import javax.swing.JSplitPane

class CompareEditorsSplitPaneFactory: SplitPaneFactory {

    override fun createSplitPane(leftPanel: JPanel, rightPanel: JPanel): JSplitPane {
        val splitPane = JSplitPane();
        splitPane.customize()
        splitPane.addEditors(leftPanel, rightPanel)
        return splitPane
    }
}