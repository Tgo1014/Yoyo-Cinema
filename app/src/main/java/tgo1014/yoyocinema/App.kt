package tgo1014.yoyocinema

import android.app.Application
import androidx.room.Room
import tgo1014.yoyocinema.data.repositories.database.AppDatabase

class App : Application() {

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "db").build()
    }
}