package tgo1014.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tgo1014.data.local.converters.GenreTypeConverter
import tgo1014.data.local.converters.ProductionCompanyTypeConverter
import tgo1014.data.local.converters.ProductionCountryTypeConverter
import tgo1014.data.local.converters.SpokenLanguageTypeConverter
import tgo1014.data.local.dao.MoviesDao
import tgo1014.data.local.entities.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(value = [
    GenreTypeConverter::class,
    ProductionCompanyTypeConverter::class,
    ProductionCountryTypeConverter::class,
    SpokenLanguageTypeConverter::class
])
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}