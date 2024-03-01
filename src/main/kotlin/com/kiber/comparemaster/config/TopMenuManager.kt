package com.kiber.comparemaster.config

import com.kiber.comparemaster.action.PluginAction

object TopMenuManager {
    internal var sealed: Boolean = false

    private val actions: MutableList<PluginAction> = mutableListOf()

    fun add(vararg actions: PluginAction) {
        if(!sealed) {
            this.actions.addAll(actions)
        }
    }
}