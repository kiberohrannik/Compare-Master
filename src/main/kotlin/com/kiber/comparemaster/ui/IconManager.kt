package com.kiber.comparemaster.ui

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.IconManager
import javax.swing.Icon

object IconManager {

    val swapFiles: Icon = load("icons/swap.svg")
    val replaceOnlyValues: Icon = load("icons/replace-values.svg")
    val formatText: Icon = load("icons/format.svg")
    val inlineText: Icon = load("icons/inline.svg")

    private fun load(path: String): Icon {
        return IconLoader.getIcon(path, this.javaClass.classLoader)
    }
}