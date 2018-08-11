package tgo1014.yoyocinema

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.squareup.picasso.Picasso
import tgo1014.yoyocinema.data.repositories.database.AppDatabase

class App : Application() {

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "db").build()

//        val picassoInstance = Picasso.Builder(this)
//                .listener { picasso, uri, exception ->
//                    Log.e(exception.toString(), "Failed to load image: $uri")
//                }.build()
//        Picasso.setSingletonInstance(picassoInstance)
    }
}