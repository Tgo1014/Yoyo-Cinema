package tgo1014.yoyocinema.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import tgo1014.presentation.viewmodels.movies.FavoritesVM
import tgo1014.presentation.viewmodels.movies.MovieByIdVM
import tgo1014.presentation.viewmodels.movies.MoviesSearchVM

val presentationModule = module {
    viewModel { MoviesSearchVM(searchMoviesUseCase = get()) }
    viewModel { MovieByIdVM(getMovieByIdUseCase = get()) }
    viewModel {
        FavoritesVM(
                favoriteMovieUseCase = get(),
                getFavoriteMoviesListUseCase = get(),
                unfavoriteMovieUseCase = get()
        )
    }
}