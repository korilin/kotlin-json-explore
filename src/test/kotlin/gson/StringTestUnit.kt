package gson

import com.google.gson.GsonBuilder
import gson.deserializer.BooleanDeserializer
import gson.deserializer.StringDeserializer
import org.junit.jupiter.api.Test


private val json = """
    {
        "str": "abc",
        "str1": true,
        "str2": 123,
        "str3": [],
        "str4": {},
        "str5": null
    }
""".trimIndent()


private data class StringModel(
    val str: String = "",
    val str1: String = "",
    val str2: String = "",
    val str3: String = "",
    val str4: String = "",
    val str5: String = "",
)


class StringTestUnit {

    private val builder = GsonBuilder()

    @Test
    fun testString() {
        for (deserializer in StringDeserializer.adapters) {
            builder.registerTypeAdapter(deserializer.forType, deserializer)
        }
        val gson = builder.create()
        println(gson.fromJson(json, StringModel::class.java))
    }
}