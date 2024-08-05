package com.kiber.comparemaster.config

import com.kiber.comparemaster.action.FilePairAction
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.action.PopupAction
import kotlinx.collections.immutable.toImmutableList

object SideMenuManager {
    internal var sealed: Boolean = false

    private val actions: MutableList<FilePairAction> = mutableListOf()
    private val popups: MutableList<PopupAction> = mutableListOf()
    private val otherActions: MutableList<PluginAction> = mutableListOf()

    fun add(vararg actions: FilePairAction) {
        if(!sealed) {
            this.actions.addAll(actions)
        }
    }

    fun add(popup: PopupAction) {
        if(!sealed) {
            popups.add(popup)
        }
    }

    fun getActions(): List<FilePairAction> = actions.toImmutableList()

    fun getPopups(): List<PopupAction> = popups.toImmutableList()

    internal fun add(popup: PluginAction) {
        if(!sealed) {
            otherActions.add(popup)
        }
    }

    internal fun getOtherActions(): List<PluginAction> = otherActions.toImmutableList()
}