package com.kiber.comparemaster.old

import com.intellij.icons.AllIcons
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

//class AskQuestionAction : ToggleAction({ -> ""}, AllIcons.Actions.AddFile) {
class AskQuestionAction : AnAction({ -> ""}, AllIcons.Actions.AddFile) {
    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.browse("https://stackoverflow.com/questions/ask")
    }

    override fun displayTextInToolbar(): Boolean {
        return true
    }

}