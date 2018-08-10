package tgo1014.yoyocinema.data.repositories.converters

import androidx.room.TypeConverter
import tgo1014.yoyocinema.helpers.extensions.fromJson
import tgo1014.yoyocinema.helpers.extensions.toJson

open class AbstractTypeConverter<T> {

    @TypeConverter
    fun to(item: T?): String? = item.toJson()

    @TypeConverter
    fun from(item: String?): T? = item?.let { item.fromJson<T>() }

}