package com.kiber.comparemaster.config

import com.intellij.icons.AllIcons
import com.kiber.comparemaster.action.FilePairAction
import com.kiber.comparemaster.action.PopupAction
import com.kiber.comparemaster.action.ShowDiffAction
import com.kiber.comparemaster.function.ClearContentFunction
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.function.FilePairFuncWrapper
import com.kiber.comparemaster.function.SwapFilesFunction
import com.kiber.comparemaster.function.json.*
import com.kiber.comparemaster.function.xml.*
import com.kiber.comparemaster.ui.IconManager

@PluginConfiguration
object BasicActionsLoader : ActionsLoader {

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
            function = FilePairFuncWrapper(SortJsonFunction, SortXmlFunction)
        )

        val formatFunction = FilePairAction(
            hint = "Format",
            icon = IconManager.formatText,
            function = FilePairFuncWrapper(FormatJsonFunction, FormatXmlFunction),
        )

        val inlineFunction = FilePairAction(
            hint = "Inline",
            icon = IconManager.inlineText,
            function = FilePairFuncWrapper(InlineJsonFunction, InlineXmlFunction),
        )

        val replaceOnlyValuesAction = FilePairAction(
            hint = "Replace field values from left to right",
            icon = IconManager.replaceOnlyValues,
            function = FilePairFuncWrapper(
                JsonReplaceOnlyPresentValuesFunction(),
                XmlReplaceOnlyPresentValuesFunction()
            )
        )

        val addAbsentValuesAction = FilePairAction(
            hint = "Add absent values from left to right",
            icon = IconManager.addAbsentValues,
            function = FilePairFuncWrapper(JsonAddAbsentFieldsFunction(), XmlAddAbsentFieldsFunction())
        )

        val escapeFunction = FilePairAction(
            hint = "Escape",
            icon = IconManager.escape,
            function = FilePairFuncWrapper(EscapeJsonFunction, EscapeXmlFunction),
        )

        val unescapeFunction = FilePairAction(
            hint = "Unescape",
            icon = IconManager.unescape,
            function = FilePairFuncWrapper(UnescapeJsonFunction, UnescapeXmlFunction),
        )

        val editPopupAction = PopupAction(
            hint = "Edit",
            icon = IconManager.modify,
            actions = listOf(escapeFunction, unescapeFunction, replaceOnlyValuesAction, addAbsentValuesAction)
        )

        val showDiffAction = ShowDiffAction()

        SideMenuManager.add(clearAction, copyAction, swapAction, sortAction, formatFunction, inlineFunction)
        SideMenuManager.add(editPopupAction)
        SideMenuManager.add(showDiffAction)
    }
}