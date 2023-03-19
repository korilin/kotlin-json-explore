package gson.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import java.lang.reflect.Type

sealed class BooleanDeserializer<T>: PrimitiveDeserializer<T>() {

    companion object {
        const val DEFAULT_VALUE = false
        val adapters = PrimitiveBooleanDeserializer() + NullableBooleanDeserializer()
    }
}

class PrimitiveBooleanDeserializer: IntDeserializer<Boolean>() {

    override val forType: Type = Boolean::class.java

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Boolean {
        checkJsonElement(json, typeOfT)
        return delegate.fromJson(json, typeOfT) ?: BooleanDeserializer.DEFAULT_VALUE
    }

}

class NullableBooleanDeserializer: IntDeserializer<Boolean?>() {

    override val forType: Type = java.lang.Boolean::class.java

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Boolean? {
        checkJsonElement(json, typeOfT)
        return delegate.fromJson(json, typeOfT)
    }
}
