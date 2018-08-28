package tgo1014.yoyocinema.old

import android.app.Application
import android.arch.persistence.room.Room
import tgo1014.yoyocinema.old.data.repositories.database.AppDatabase

class App : Application() {

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "db").build()
    }
}