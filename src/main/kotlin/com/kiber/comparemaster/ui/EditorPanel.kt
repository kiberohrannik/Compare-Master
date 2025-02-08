package com.kiber.comparemaster.ui

import com.intellij.openapi.fileEditor.FileEditor
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel

object EditorPanel {

    const val LEFT_PANEL_NAME = "leftPanel"
    const val RIGHT_PANEL_NAME = "rightPanel"

    fun createLeft(editor: FileEditor, buttons: List<JButton> = listOf()): JPanel =
        create(LEFT_PANEL_NAME, editor, buttons)

    fun createRight(editor: FileEditor, buttons: List<JButton> = listOf()): JPanel =
        create(RIGHT_PANEL_NAME, editor, buttons)


    private fun create(name: String, editor: FileEditor, buttons: List<JButton> = listOf()): JPanel {
        return JPanel()
            .apply {
                this.name = name
                layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
                add(editor.component)
                for (button in buttons) {
                    add(button)
                }
            }
    }
}