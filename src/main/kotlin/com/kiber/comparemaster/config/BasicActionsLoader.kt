package com.kiber.comparemaster.config

import com.intellij.icons.AllIcons
import com.kiber.comparemaster.action.FilePairAction
import com.kiber.comparemaster.action.PopupAction
import com.kiber.comparemaster.action.ShowDiffAction
import com.kiber.comparemaster.function.ClearContentFunction
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.function.SwapFilesFunction
import com.kiber.comparemaster.function.json.*
import com.kiber.comparemaster.ui.IconManager

@PluginConfiguration
object BasicActionsLoader: ActionsLoader {

    override fun load() {
        setupSideMenu()
    }


    private fun setupSideMenu() {
        val clearAction = FilePairAction(
            hint = "Clean all",
            icon = IconManager.cleanAll,
            function = ClearContentFunction()
        )

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

        val sortAction = FilePairAction(
            hint = "Sort",
            icon = AllIcons.ObjectBrowser.Sorted,
            function = SortJsonFunction
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
            hint = "Replace field values from left to right",
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

        val showDiffAction = ShowDiffAction()

        SideMenuManager.add(clearAction, copyAction, swapAction, sortAction, formatJsonFunction, inlineJsonFunction, )
        SideMenuManager.add(popupAction)
        SideMenuManager.add(showDiffAction)
    }
}