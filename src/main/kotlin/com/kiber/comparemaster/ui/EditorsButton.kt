package com.kiber.comparemaster.ui

import com.intellij.openapi.application.runUndoTransparentWriteAction
import javax.swing.JButton

object EditorsButton {

    fun createButton(name: String, action: () -> Unit): JButton {
        val button = JButton(name)

        button.addActionListener {
            runUndoTransparentWriteAction {
                action.invoke()
            }
        }

        return button
    }
}