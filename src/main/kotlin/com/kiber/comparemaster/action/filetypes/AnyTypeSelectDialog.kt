package com.kiber.comparemaster.action.filetypes

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.FormBuilder
import com.intellij.ui.TextFieldWithAutoCompletion
import com.intellij.ui.TextFieldWithAutoCompletionListProvider
import java.awt.Dimension
import javax.swing.JComponent

class AnyTypeSelectDialog(
    project: Project,
    options: List<String>,
    initialValue: String
) : DialogWrapper(project, true) {

    private val completionProvider = object : TextFieldWithAutoCompletionListProvider<String>(options) {
        override fun getLookupString(item: String): String = item
    }

    private val field = TextFieldWithAutoCompletion(
        project,
        completionProvider,
        true,
        initialValue
    ).apply {
        setOneLineMode(true)
        preferredSize = Dimension(320, 28)
        minimumSize = Dimension(320, 28)
    }

    init {
        title = "Select Generic File Type"
        init()
        field.selectAll()
    }

    override fun createCenterPanel(): JComponent {
        return FormBuilder.createFormBuilder()
            .addLabeledComponent("Type or choose format:", field)
            .panel
    }

    override fun getPreferredFocusedComponent(): JComponent = field

    fun selectedValue(): String = field.text.trim()
}
