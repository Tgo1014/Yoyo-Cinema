package tgo1014.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tgo1014.data.local.entities.Movie


object ProductionCompanyTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Movie.ProductionCountry> {
        val listType = object : TypeToken<List<Movie.ProductionCountry>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayLisr(list: List<Movie.ProductionCountry>): String {
        return Gson().toJson(list)
    }
}