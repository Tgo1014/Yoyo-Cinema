package tgo1014.yoyocinema.data.repositories.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.data.repositories.converters.*
import tgo1014.yoyocinema.data.repositories.daos.MoviesDao

@Database(entities = [(Movie::class)], version = 1)
@TypeConverters(
        BelongsToCollectionTypeConverter::class,
        GenreTypeConverter::class,
        ProductionCountryTypeConverter::class,
        ProductionCompanyTypeConverter::class,
        SpokenLanguageTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}