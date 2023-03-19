package gson.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import java.lang.reflect.Type

sealed class StringDeserializer<T> : PrimitiveDeserializer<T>() {

    protected fun parseString(json: JsonElement): String? {
        return if (json.isJsonPrimitive) {
            json.asString
        } else if (json.isJsonObject) {
            println("is obj")
            json.asJsonObject.toString()
        } else if (json.isJsonArray) {
            println("is array")
            json.asJsonArray.toString()
        } else null
    }


    companion object {
        val adapters = PrimitiveStringDeserializer() + PrimitiveStringDeserializer()
    }
}

private class PrimitiveStringDeserializer : StringDeserializer<String>() {

    override val forType: Type = String::class.java

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String {
        checkJsonElement(json, typeOfT)
        if (json == null) return ""
        val parse = parseString(json)
        return parse ?: ""
    }

}

private class NullableStringDeserializer : StringDeserializer<String?>() {

    override val forType: Type = String::class.java

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String? {
        checkJsonElement(json, typeOfT)
        if (json == null) return null
        return parseString(json)
    }
}
