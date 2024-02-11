package com.kiber.comparemaster.ui

import com.intellij.util.ui.JBUI
import javax.swing.JPanel
import javax.swing.JSplitPane

fun JSplitPane.customize() {
    this.setDividerSize(1);
    this.orientation = JSplitPane.HORIZONTAL_SPLIT
    this.border = JBUI.Borders.empty()
}

fun JSplitPane.addEditors(leftEditor: JPanel, rightEditor: JPanel) {
    this.leftComponent = leftEditor
    this.rightComponent = rightEditor
}