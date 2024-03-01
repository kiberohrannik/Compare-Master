package com.kiber.comparemaster.exception

import kotlin.reflect.KClass

class NullComponentException(componentClass: KClass<*>, componentId: String?) :
    RuntimeException(
        if (componentId == null) "${componentClass.qualifiedName} can't be NULL!"
        else "${componentClass.qualifiedName} with id = $componentId can't be NULL!"
    )