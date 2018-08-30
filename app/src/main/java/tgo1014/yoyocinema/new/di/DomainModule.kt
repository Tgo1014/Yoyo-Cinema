package tgo1014.yoyocinema.new.di

import org.koin.dsl.module.module
import tgo1014.domain.usecases.movies.SearchForMovie

val domainModule = module {
    single {
        SearchForMovie(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
}