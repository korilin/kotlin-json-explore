package gson

import com.google.gson.GsonBuilder
import gson.deserializer.IntDeserializer
import org.junit.jupiter.api.Test

private val json = """
    {
        "int": 1,
        "nullableInt": 0,
        "intStr": "0",
        "intOtherNum": 1.1,
        "intBool": true
    }
""".trimIndent()

private data class IntTester(
    val int: Int = 0,
    val nullableInt: Int? = null,
    val intStr: Int = 0,
    val intOtherNum: Int = 0,
    val intBool: Int = 0
)

class IntTestUnit {

    private val builder = GsonBuilder()

    @Test
    fun testInt() {
        for (deserializer in IntDeserializer.adapters) {
            builder.registerTypeAdapter(deserializer.forType, deserializer)
        }
        val gson = builder.create()
        println(gson.fromJson(json, IntTester::class.java))
    }
}