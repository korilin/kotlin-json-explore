import com.google.gson.*
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
import org.junit.jupiter.api.Test
import java.lang.reflect.Type
import kotlin.reflect.javaType
import kotlin.reflect.typeOf


internal class EntityCreator : InstanceCreator<Entity?> {
    override fun createInstance(type: Type?): Entity {
        return Entity("", "")
    }
}


private val json = """
        {
            "a": "A",
            "b": {"b1": 1}
        }
    """.trimIndent()

val stringDecode = JsonDeserializer<String> { json, typeOfT, context ->
    println("$json, $typeOfT, $context")
    if (json.isJsonObject) json.toString() else json.asString
}

data class Entity(
    val a: String,
    val b: String = "",
) {
    val c: Boolean = true

    init {
        println("init")
    }

    override fun toString(): String {
        return "${this::class.simpleName}(a:$a, b:$b, c:$c)"
    }
}

class GsonToJsonTest {

    // 使用 Unsafe 实例来创建，绕过构造方法和初始化代码
    // 1. 默认值问题，如果 primary constructor 只有部分参数有默认值，那么 Gson 解析将不会使用默认值，因为没有无参构造方法

    private val gson = GsonBuilder()
        .registerTypeAdapter(Entity::class.java, EntityCreator())
        .registerTypeAdapter(String::class.java, stringDecode)
        .create()

    @Test
    fun toJson() {
        val j = gson.fromJson(json, Entity::class.java)
        println(j)
    }

}