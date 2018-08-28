package tgo1014.yoyocinema.old.data.repositories.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tgo1014.yoyocinema.old.data.entities.Movie
import tgo1014.yoyocinema.old.helpers.extensions.fromJson

class SpokenLanguageTypeConverter {
    @TypeConverter
    fun to(item: List<Movie.SpokenLanguage>?): String? {
        return Gson().toJson(item, object : TypeToken<List<Movie.SpokenLanguage>>() {}.type)
    }

    @TypeConverter
    fun from(item: String?): List<Movie.SpokenLanguage>? = item?.let { item.fromJson<List<Movie.SpokenLanguage>>() }
}
