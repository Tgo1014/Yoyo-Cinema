package tgo1014.yoyocinema.new

import android.app.Application
import org.koin.android.ext.android.startKoin
import tgo1014.yoyocinema.new.di.androidModule
import tgo1014.yoyocinema.new.di.domainModule
import tgo1014.yoyocinema.new.di.persistenceModule
import tgo1014.yoyocinema.new.di.presentationModule

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this,
                listOf(androidModule,
                        domainModule,
                        persistenceModule,
                        presentationModule)
        )
    }

}