package mexan1ck.config

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class Configuration {
    fun readConfig(fileName: String): JsonObject {
        val parser = Parser()
        return parser.parse(fileName) as JsonObject
    }
    fun castResultToMap(result: JsonObject,key: String): MutableMap<String, *> {
        val arr = result[key] as JsonArray<*>
        val obj = arr[0] as JsonObject
        return obj.map
    }
}