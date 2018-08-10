package tgo1014.yoyocinema.helpers.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> T.toJson(): String {
    return Gson().toJson(this, object : TypeToken<T>() {}.type)
}

fun <T> String.fromJson(): T {
    return Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)
}