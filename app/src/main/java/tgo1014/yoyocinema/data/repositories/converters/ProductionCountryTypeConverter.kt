package tgo1014.yoyocinema.data.repositories.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.helpers.extensions.fromJson

class ProductionCountryTypeConverter{
    @TypeConverter
    fun to(item: List<Movie.ProductionCountry>?): String? {
        return Gson().toJson(item, object : TypeToken<List<Movie.ProductionCountry>>() {}.type)
    }

    @TypeConverter
    fun from(item: String?): List<Movie.ProductionCountry>? = item?.let { item.fromJson<List<Movie.ProductionCountry>>() }
}