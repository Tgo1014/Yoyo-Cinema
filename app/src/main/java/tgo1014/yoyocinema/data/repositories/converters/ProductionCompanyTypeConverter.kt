package tgo1014.yoyocinema.data.repositories.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.helpers.extensions.fromJson

class ProductionCompanyTypeConverter {
    @TypeConverter
    fun to(item: List<Movie.ProductionCompany>?): String? {
        return Gson().toJson(item, object : TypeToken<List<Movie.ProductionCompany>>() {}.type)
    }

    @TypeConverter
    fun from(item: String?): List<Movie.ProductionCompany>? = item?.let { item.fromJson<List<Movie.ProductionCompany>>() }
}