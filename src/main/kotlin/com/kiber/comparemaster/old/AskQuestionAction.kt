package com.kiber.comparemaster.old

import com.intellij.icons.AllIcons
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction

class AskQuestionAction : ToggleAction({ -> "123"}, AllIcons.Actions.AddFile) {
    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.browse("https://stackoverflow.com/questions/ask")
    }

//    AllIcons.Actions.AddFile

    override fun isSelected(p0: AnActionEvent): Boolean {
        return false
    }

    override fun setSelected(p0: AnActionEvent, p1: Boolean) {
//        TODO("Not yet implemented")
    }

    override fun displayTextInToolbar(): Boolean {
        return true
    }

}