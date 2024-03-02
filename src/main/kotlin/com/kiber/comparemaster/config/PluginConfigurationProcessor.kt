package com.kiber.comparemaster.config

import com.fasterxml.jackson.module.kotlin.isKotlinClass
import com.jetbrains.rd.util.reflection.scanForClasses
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.typeOf

const val PLUGIN_PACKAGE_NAME = "com.kiber.comparemaster"

class PluginConfigurationProcessor {

    fun loadAnnotated() {
        getAnnotatedLoaders().forEach { clazz ->
            val loadFunc = clazz.functions.find { func ->
                !func.isExternal
                        && func.name == "load"
                        && func.returnType == typeOf<Unit>()
                        && func.parameters.size == 1
            }

            if(clazz.objectInstance != null) {
                loadFunc?.call(clazz.objectInstance)
            } else {
                loadFunc?.call(clazz.primaryConstructor?.call())
            }
        }
    }


    private fun getAnnotatedLoaders(): MutableList<KClass<*>> {
        val packages = this::class.java.classLoader.definedPackages
            .filter { p -> p.name.startsWith(PLUGIN_PACKAGE_NAME) }
            .map { p -> p.name }

        return this::class.java.classLoader.scanForClasses(*packages.toTypedArray())
            .filter { clazz -> clazz.isKotlinClass() && ActionsLoader::class.java.isAssignableFrom((clazz)) }
            .map { clazz -> clazz.kotlin }
            .filter { clazz -> clazz.hasAnnotation<PluginConfiguration>() }
            .toCollection(mutableListOf())

    }
}