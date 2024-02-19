package com.kiber.comparemaster.function

import ai.grazie.text.find
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.flipkart.zjsonpatch.JsonDiff
import com.flipkart.zjsonpatch.JsonPatch
import com.kiber.comparemaster.content.parser.JsonPatchMapper
import org.junit.Test
import java.io.ByteArrayInputStream
import java.util.*


class HighlightDifferentValuesFunctionSuite {

    private val mapper = ObjectMapper()

    @Test
    fun `json parser`() {
        val inStream = ByteArrayInputStream(byteArrayOf())
        val json1 = "{\"name\": \"ABC\", \"city\": \"XYZ\"}"
        val json2 = "{\"city\": \"XYZ\", \"name\": \"ABC\"}"

        val json3 = "{ \"op\": \"move\", \"from\": \"/account_id\", \"path\": \"/app_id\" }"

        val sourceNode = mapper.readTree("{\"name\": \"ABC\", \"city\": \"XYZ\"}")
        val targetNode = mapper.readTree("{\"name\": \"XYZ\", \"city\": \"XYZ\"}")

        //https://smyachenkov.com/posts/json-difference-in-java/
        // https://github.com/flipkart-incubator/zjsonpatch

        val patch = JsonDiff.asJson(sourceNode, targetNode)
        println("===============================")
        println("PATCH: $patch")

        val res = mapper
            .reader()
            .forType(object : TypeReference<List<JsonPatchOp>>() {})
            .readValue<List<JsonPatchOp>>(patch)

        println("\n\n res $res")
    }

    @Test
    fun `operation parser`() {
        //{"op":"replace","path":"/name","value":"XYZ"},{"op":"replace","path":"/city","value":"ABC"}
        val res = mapper.writeValueAsString(JsonPatchOp("replace", "name", "XYZ"))
        println("\n\n $res")
    }

    @Test
    fun `find textrange`() {
        //{"op":"replace","path":"/name","value":"XYZ"}

//        val json1 = "{\"name\": \"ABC\", \"city\": \"XYZ\"}"
        val json1 = "{\n" +
                "  \"name\": \"XYZ\",\n" +
                "  \"city\": \"XYZ\"\n" +
                "}"
        val json2 = "{\"name\": \"XYZ\", \"city\": \"XYZ\"}"

        val operation = JsonPatchOp("replace", "/name", "XYZ")

        val textSearch = "\"name\": \"XYZ\""
        val range = json2.find(textSearch)

        println("\n\n RANGE: $range")
    }

    @Test
    fun `replace only values`() {
        val json1 = "{\n" +
                "  \"employee\": {\n" +
                "    \"name\": \"sonoo1\",\n" +
                "    \"salary\": 56000,\n" +
                "    \"married\": true,\n" +
                "    \"married_1\": true,\n" +
                "    \"married_23\": true,\n" +
                "    \"trash\": [1, 2, 3]\n" +
                "  }\n" +
                "}"
        val json2 = "{\"employee\":{\"name\":\"sonoo\",\"salary\":56000,\"married\":true, \"trash\": [1, 2]}}"

        val operations = JsonPatchMapper.toJsonPatch(json1, json2)
        println(operations)

        val replaceValuesOperations = operations.filter {
            it.op == "replace" || it.path.matches(Regex(".*/\\d$"))
            //TODO check also that json does not contain node with ".*/\\d$" pattern like "salary/1":2456
        }
        println("\n\nreplaceValuesOperations = $replaceValuesOperations \n\n")


        val operationsNode = mapper.readTree(mapper.writeValueAsString(replaceValuesOperations))
        val replaced = JsonPatch.apply(operationsNode, mapper.readTree(json1))
        println("replaced = ${replaced.toPrettyString()}")

        //TODO for highlight add textRanges of differences between 2 strings (in values only)
    }

    @Test
    fun `highlight different values`() {
        val json1 = "{\n" +
                "  \"employee\": {\n" +
                "    \"married\": true,\n" +
                "    \"name\": \"sonoo1\",\n" +
                "    \"salary\": 56000,\n" +
                "    \"married_23\": true,\n" +
                "    \"married_1\": true,\n" +
                "    \"trash\": [1, 2, 3]\n" +
                "  }\n" +
                "}"
        val json2 = "{\"employee\":{\"name\":\"sonoo\",\"salary\":56000,\"married\":true, \"trash\": [1, 2]}}"

        val operations = JsonPatchMapper.toJsonPatch(json1, json2)
        println(operations)

        val replaceValuesOperations = operations.filter {
            it.op == "replace" || it.path.matches(Regex(".*/\\d$"))
            //TODO check also that json does not contain node with ".*/\\d$" pattern like "salary/1":2456
        }
        println("\n\nreplaceValuesOperations = $replaceValuesOperations \n\n")


        val operationsNode = mapper.readTree(mapper.writeValueAsString(replaceValuesOperations))
        val replaced = JsonPatch.apply(operationsNode, mapper.readTree(json1))
        val replacedStr = replaced.toPrettyString()

        //TODO think about more performative way to find textRanges where text differs
        val localMapper = JsonMapper.builder().nodeFactory(SortingNodeFactory()).build()
        val sortedJson1 = localMapper.readTree(json1).toPrettyString()
        val sortedJson2 = localMapper.readTree(replacedStr).toPrettyString()

        println("============================")
        println("sortedJson1 = ${sortedJson1}")
        println("\n\n")
        println("sortedJson1 = ${sortedJson2}")
        println("============================")

        //TODO for highlight add textRanges of differences between 2 strings (in values only)
    }

//    private fun getLinesDiff(prettyJson1: String, prettyJson2: String): List<Int> {
//        val lines1 = prettyJson1.lines()
//        val lines2 = prettyJson2.lines()
//
//        val diffLines = mutableListOf<Pair<Int, Int>>()
//
//        if (lines1.size < lines2.size) {
//            var index2 = 0
//            lines1.forEachIndexed { index, line ->
//                {
//                    val line2 = lines2.get(index)
//
//                    if (line.contains(":") && line2.contains(":")) {
//                        parseLine(line, line2, index, index2, diffLines)
//                        } else {
//                            index2++
//
//                        }
//                    }
//                }
//            }
//        }
//    }

//    private fun parseLine(
//        line: String, line2: String,
//        index: Int, index2: Int,
//        diffLines: List<Pair<Int, Int>>
//    ) {
//        val field1 = mapper.readTree(line).fields().next()
//        val field2 = mapper.readTree(line2).fields().next()
//
//        if (field1.key == field2.key) {
//            if (field1.value != field2.value) {
//                diffLines.addLast(Pair(index, index2))
//            }
//        }

        @Test
        fun `find textrange2`() {
            val json1 = "{\n" +
                    "  \"employee\": {\n" +
                    "    \"name\": \"sonoo1\",\n" +
                    "    \"salary\": 56000,\n" +
                    "    \"married\": true\n" +
                    "  }\n" +
                    "}"
            val json2 = "{\"employee\":{\"name\":\"sonoo\",\"salary\":56000,\"married\":true}}"

            val operations = JsonPatchMapper.toJsonPatch(json1, json2)
            println(operations)

            operations.forEach {

            }

            val textSearch = "\"name\": \"XYZ\""
            val range = json2.find(textSearch)

            println("\n\n RANGE: $range")
        }
    }

    class JsonPatchOperation() {

        private var op: String = ""
        private var path: String = ""
        private var value: String = ""

        constructor(op: String, path: String, value: String) : this() {
            this.op = op
            this.path = path
            this.value = value
        }
    }

    data class JsonPatchOp(var op: String, var path: String, var value: String) {
        constructor() : this("", "", "")
    }

    internal class SortingNodeFactory : JsonNodeFactory() {
        override fun objectNode(): ObjectNode {
            return ObjectNode(this, TreeMap<String, JsonNode>())
        }
    }