package com.kiber.comparemaster.content.parser.json

import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.kiber.comparemaster.action.FilePairAction
import com.kiber.comparemaster.action.PopupAction
import com.kiber.comparemaster.action.ShowDiffAction
import com.kiber.comparemaster.config.ActionsLoader
import com.kiber.comparemaster.config.PluginConfiguration
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.function.SwapFilesFunction
import com.kiber.comparemaster.function.json.AddAbsentFieldsFunction
import com.kiber.comparemaster.function.json.FormatJsonFunction
import com.kiber.comparemaster.function.json.InlineJsonFunction
import com.kiber.comparemaster.function.json.ReplaceOnlyPresentValuesFunction
import com.kiber.comparemaster.ui.IconManager

@PluginConfiguration
class TestActionsLoader: ActionsLoader {

    override fun load() {
        println("\n\nTEST !!!!! ===================================\n\n")
    }
}