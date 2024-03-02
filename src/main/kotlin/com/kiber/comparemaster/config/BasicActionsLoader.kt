package com.kiber.comparemaster.config

import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.kiber.comparemaster.action.FilePairAction
import com.kiber.comparemaster.action.PopupAction
import com.kiber.comparemaster.action.ShowDiffAction
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.function.SwapFilesFunction
import com.kiber.comparemaster.function.json.AddAbsentFieldsFunction
import com.kiber.comparemaster.function.json.FormatJsonFunction
import com.kiber.comparemaster.function.json.InlineJsonFunction
import com.kiber.comparemaster.function.json.ReplaceOnlyPresentValuesFunction
import com.kiber.comparemaster.ui.IconManager

@PluginConfiguration
object BasicActionsLoader: ActionsLoader {

    override fun load() {
        setupTopMenu()
        setupSideMenu()

        println("\n\nBasicActionsLoader !!!!! ===================================\n\n")

    }

    private fun setupTopMenu() {
        TopMenuManager.add(ShowDiffAction())
    }

    private fun setupSideMenu() {
        val copyAction = FilePairAction(
            hint = "Copy to right editor",
            icon = AllIcons.Actions.Copy,
            function = CopyContentFunction()
        )

        val swapAction = FilePairAction(
            hint = "Swap",
            icon = IconManager.swapFiles,
            function = SwapFilesFunction
        )

        val formatJsonFunction = FilePairAction(
            hint = "Format",
            icon = IconManager.formatText,
            function = FormatJsonFunction,
        )

        val inlineJsonFunction = FilePairAction(
            hint = "Inline",
            icon = IconManager.inlineText,
            function = InlineJsonFunction,
        )

        val replaceOnlyValuesAction = FilePairAction(
            hint = "Replace existing values from left to right",
            icon = null,
            function = ReplaceOnlyPresentValuesFunction()
        )

        val addAbsentValuesAction = FilePairAction(
            hint = "Add absent values from left to right",
            icon = null,
            function = AddAbsentFieldsFunction()
        )

        val popupAction = PopupAction(
            hint = "Replace",
            icon = IconManager.replaceOnlyValues,
            actions = listOf(replaceOnlyValuesAction, addAbsentValuesAction)
        )

        SideMenuManager.add(copyAction, swapAction, formatJsonFunction, inlineJsonFunction)
        SideMenuManager.add(popupAction)
    }
}