package com.kiber.comparemaster.ui

import com.intellij.openapi.fileEditor.FileEditor
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel

object EditorPanel {

    fun create(editor: FileEditor, buttons: List<JButton>): JPanel {
        return JPanel()
            .apply {
                layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
                add(editor.component)
                for (button in buttons) {
                    add(button)
                }
            }
    }
}