package com.kiber.comparemaster

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.jetbrains.kotlin.idea.refactoring.hostEditor

class EditorActionHandler: AnAction() {
    /**
     * public class EditorIllustrationAction extends AnAction {
     *   @Override
     *   public void update(@NotNull AnActionEvent event) {
     *     // Get required data keys
     *     Project project = event.getProject();
     *     Editor editor = event.getData(CommonDataKeys.EDITOR);
     *     // ...
     *   }
     * }
     */
    override fun actionPerformed(p0: AnActionEvent) {
        TODO("Not yet implemented")
    }

    override fun update(e: AnActionEvent) {
        println("\n\nEDITOOOOOR\n\n")

        val editor = e.dataContext.hostEditor!!
        println(editor.document.text)
    }
}