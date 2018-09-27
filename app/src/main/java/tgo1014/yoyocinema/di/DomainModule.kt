package tgo1014.yoyocinema.di

import org.koin.dsl.module.module
import tgo1014.domain.usecases.movies.*

val domainModule = module {
    single {
        SearchForMovieUseCase(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
    single {
        GetMovieByIdUseCase(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
    single {
        FavoriteMovieUseCase(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
    single {
        GetFavoriteMoviesListUseCase(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
    single {
        UnfavoriteMovieUseCase(
                moviesRepository = get(),
                postExecutionThread = get()
        )
    }
}