package gson

import com.google.gson.GsonBuilder
import gson.deserializer.BooleanDeserializer
import org.junit.jupiter.api.Test

private val json = """
    {
        "bool": true,
        "nullableBool": false
    }
""".trimIndent()

private data class BooleanTester(
    val bool: Boolean = true,
    val nullableBool: Boolean? = null
)

class BooleanTestUnit {

    private val builder = GsonBuilder()

    @Test
    fun testBoolean() {
        for (deserializer in BooleanDeserializer.adapters) {
            builder.registerTypeAdapter(deserializer.forType, deserializer)
        }
        val gson = builder.create()
        println(gson.fromJson(json, BooleanTester::class.java))
    }
}