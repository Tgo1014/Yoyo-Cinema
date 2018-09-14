package tgo1014.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tgo1014.data.local.entities.Movie


object SpokenLanguageTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Movie.SpokenLanguage> {
        val listType = object : TypeToken<List<Movie.SpokenLanguage>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayLisr(list: List<Movie.SpokenLanguage>): String {
        return Gson().toJson(list)
    }
}