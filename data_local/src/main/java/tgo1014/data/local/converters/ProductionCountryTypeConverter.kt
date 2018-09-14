package tgo1014.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tgo1014.data.local.entities.Movie


object ProductionCountryTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Movie.ProductionCompany> {
        val listType = object : TypeToken<List<Movie.ProductionCompany>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayLisr(list: List<Movie.ProductionCompany>): String {
        return Gson().toJson(list)
    }
}