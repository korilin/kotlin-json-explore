package gson

import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Test


class GsonToJsonTest {

    // 使用 Unsafe 实例来创建，绕过构造方法和初始化代码
    // 1. 默认值问题，如果 primary constructor 只有部分参数有默认值，那么 Gson 解析将不会使用默认值，因为没有无参构造方法

    private val gson = GsonBuilder().serializeNulls().create()

    private val json = """
        {
            "a": "A",
            "b": 1,
            "c": null,
            "d": true
        }
    """.trimIndent()

    private class J(
        val a: String, val b: Int = 2, val c: Boolean = true
    ) {

        private var e: Boolean = true

        init {
            println("init")
        }

        override fun toString(): String {
            return "{$a, $b, $c, $e}"
        }
    }

    @Test
    fun toJson() {
        val j = gson.fromJson(json, J::class.java)
        println(j)
    }

}