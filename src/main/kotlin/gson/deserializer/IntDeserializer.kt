package gson.deserializer

import com.google.gson.*
import java.lang.reflect.Type

/**
 * The type mapping is as follows:
 * - `Int` corresponds to Java primitive type `int`
 * - `Int?` corresponds to Java packaging type [Integer]
 */
sealed class IntDeserializer<T> : PrimitiveDeserializer<T>() {

    protected fun parseInt(json: JsonElement): Int? {
        return tryCast { json.asInt } ?: tryCast { json.asString.toInt() } ?: tryCast { if (json.asBoolean) 1 else 0 }
    }

    companion object {
        val adapters = PrimitiveIntDeserializer() + NullableIntDeserializer()
    }
}

private class PrimitiveIntDeserializer : IntDeserializer<Int>() {

    override val forType: Type = Int::class.java

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
        checkJsonElement(json, typeOfT)
        if (json == null) return 0
        val int = parseInt(json)
        return int ?: delegate.fromJson(json, typeOfT) ?: 0
    }

}

private class NullableIntDeserializer : IntDeserializer<Int?>() {

    override val forType: Type = Integer::class.java

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int? {
        checkJsonElement(json, typeOfT)
        if (json == null) return null
        val int = parseInt(json)
        return int ?: delegate.fromJson(json, typeOfT)
    }
}
