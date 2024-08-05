package com.kiber.comparemaster.util

import kotlinx.coroutines.repackaged.net.bytebuddy.utility.RandomString
import kotlin.random.Random

object JsonGenerator {

    private val random = Random(123)


    data class TestObject2(
        var field1: String = RandomString.make(),
        var field2: String = RandomString.make(),
        var fieldLong: Long = random.nextLong(),
        var fieldList: List<String> = getList(),
        var fieldBool: Boolean = random.nextBoolean()
    )

    data class TestObject4(
        var field1: String = RandomString.make(),
        var field2: String = RandomString.make(),
        var field3: String = RandomString.make(),
        var field4: String = RandomString.make(),
        var fieldLong: Long = random.nextLong(),
        var fieldList: List<String> = getList(),
        var fieldBool: Boolean = random.nextBoolean()
    )

    fun getList(size: Int = random.nextInt(0, 10)): List<String> {
        val randomList = mutableListOf<String>()
        for(i in 0..size) {
            randomList.add(RandomString.make())
        }

        return randomList
    }
}