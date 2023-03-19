package gson.deserializer

import com.google.gson.*
import java.lang.Exception
import java.lang.reflect.Type

sealed class PrimitiveDeserializer<T> : JsonDeserializer<T> {

    protected var delegate = Gson()

    abstract val forType: Type

    operator fun plus(adapter: PrimitiveDeserializer<*>) = arrayOf(this, adapter)
    operator fun plus(adapters: Array<PrimitiveDeserializer<*>>) = arrayOf(*adapters)

    protected fun <O> tryCast(block: () -> O) : O? = try {
        block.invoke()
    } catch (e: Exception) {
        null
    }

    protected fun checkJsonElement(json: JsonElement?, typeOfT: Type?) {
        val type = when {
            json?.isJsonPrimitive == true-> "Primitive"
            json?.isJsonObject == true -> "Object"
            json?.isJsonArray == true -> "Array"
            json?.isJsonNull == true -> "Null Value"
            else -> "Null Element"
        }
        println("${this::class.simpleName} -> JsonElementType: [$type] deserialize $typeOfT")
    }

}
