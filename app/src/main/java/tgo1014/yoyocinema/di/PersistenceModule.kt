package tgo1014.yoyocinema.di

import org.koin.dsl.module.module
import tgo1014.data.LocalDataSource
import tgo1014.data.MoviesRepositoryImpl
import tgo1014.data.RemoteDataSource
import tgo1014.data.local.MoviesLocalDataSourceImpl
import tgo1014.data.remote.MoviesAuth
import tgo1014.data.remote.RemoteDataSourceImpl
import tgo1014.data.remote.MoviesWebServiceFactory
import tgo1014.domain.repositories.MoviesRepository
import tgo1014.yoyocinema.BuildConfig
import tgo1014.yoyocinema.ui.auth.MoviesAuthImpl

val persistenceModule = module {
    single {
        MoviesAuthImpl() as MoviesAuth
    }
    single {
        MoviesWebServiceFactory.makeMovieWebService(
                auth = get(),
                isDebug = BuildConfig.DEBUG
        )
    }
    single {
        MoviesLocalDataSourceImpl(context = get()) as LocalDataSource
    }
    single {
        RemoteDataSourceImpl(
                moviesService = get()
        ) as RemoteDataSource
    }
    single {
        MoviesRepositoryImpl(
                localDataSource = get(),
                remoteDataSource = get()
        ) as MoviesRepository
    }
}