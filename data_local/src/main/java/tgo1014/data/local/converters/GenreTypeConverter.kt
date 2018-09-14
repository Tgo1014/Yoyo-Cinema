package tgo1014.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tgo1014.data.local.entities.Movie


object GenreTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Movie.Genre> {
        val listType = object : TypeToken<List<Movie.Genre>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayLisr(list: List<Movie.Genre>): String {
        return Gson().toJson(list)
    }
}