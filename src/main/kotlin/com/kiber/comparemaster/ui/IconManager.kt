package com.kiber.comparemaster.ui

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object IconManager {

    val toolWindowIcon: Icon = load("icons/main.svg")
    val cleanAll: Icon = load("icons/clean.svg")
    val swapFiles: Icon = load("icons/swap.svg")
    val formatText: Icon = load("icons/format.svg")
    val inlineText: Icon = load("icons/inline.svg")
    val showDiff: Icon = load("icons/diff.svg")

    val escape: Icon = load("icons/escape.svg")
    val unescape: Icon = load("icons/unescape.svg")

    val replaceOnlyValues: Icon = load("icons/replace-values.svg")
    val addAbsentValues: Icon = load("icons/add-values.svg")

    val modify: Icon = load("icons/modify.svg")

    private fun load(path: String): Icon {
        return IconLoader.getIcon(path, this.javaClass.classLoader)
    }
}