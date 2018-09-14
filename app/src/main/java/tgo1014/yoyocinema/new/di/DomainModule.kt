package tgo1014.yoyocinema.new.di

import org.koin.dsl.module.module
import tgo1014.domain.usecases.movies.GetMovieByIdUseCase
import tgo1014.domain.usecases.movies.SearchForMovieUseCase

val domainModule = module {
    single {
        SearchForMovieUseCase(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
    single{
        GetMovieByIdUseCase(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
}