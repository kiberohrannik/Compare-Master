package com.kiber.comparemaster.config

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.impl.EditorFactoryImpl
import com.intellij.openapi.fileEditor.impl.text.TextEditorImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectCloseHandler
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.impl.PsiManagerImpl
import com.kiber.comparemaster.PLUGIN_NAME
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.content.file.JsonEditorsFileFactory
import com.kiber.comparemaster.exception.NullComponentException

class CloseHandler : ProjectCloseHandler {

    override fun canClose(project: Project): Boolean {
        WriteCommandAction.runWriteCommandAction(project) {
//            JsonEditorsFileFactory.getFilePair().right().delete(null)
//            JsonEditorsFileFactory.getFilePair().left().delete(null)



            PsiManagerImpl.getInstance(project).findFile(JsonEditorsFileFactory.getFilePair().right())!!.clearCaches()
            PsiManagerImpl.getInstance(project).findFile(JsonEditorsFileFactory.getFilePair().left())!!.clearCaches()

            JsonEditorsFileFactory.getFilePair().leftDoc().setText("")
            JsonEditorsFileFactory.getFilePair().rightDoc().setText("")

            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow(PLUGIN_NAME)
                ?: throw NullComponentException(ToolWindow::class, PLUGIN_NAME)

            toolWindow.hide()

            val panel = toolWindow.contentManager.getContent(0)!!.component as ToolWindowPanel

            EditorFactoryImpl().releaseEditor((panel.leftEditor as TextEditorImpl).editor )
            EditorFactoryImpl().releaseEditor((panel.rightEditor as TextEditorImpl).editor )

            panel.leftEditor.dispose()
//            panel.leftEditor.dispose()
            panel.rightEditor.dispose()
        }

        return true
    }
}