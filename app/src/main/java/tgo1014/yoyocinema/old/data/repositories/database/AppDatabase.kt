package tgo1014.yoyocinema.old.data.repositories.database

import tgo1014.yoyocinema.old.data.repositories.daos.MoviesDao

//@Database(entities = [(MovieBinding::class)], version = 1)
//@TypeConverters(
//        GenreTypeConverter::class,
//        ProductionCountryTypeConverter::class,
//        ProductionCompanyTypeConverter::class,
//        SpokenLanguageTypeConverter::class)
abstract class AppDatabase/* : RoomDatabase()*/ {
    abstract fun moviesDao(): MoviesDao
}