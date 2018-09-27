package tgo1014.yoyocinema

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin
import tgo1014.yoyocinema.di.androidModule
import tgo1014.yoyocinema.di.domainModule
import tgo1014.yoyocinema.di.persistenceModule
import tgo1014.yoyocinema.di.presentationModule

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this,
                listOf(androidModule,
                        domainModule,
                        persistenceModule,
                        presentationModule)
        )
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }

}