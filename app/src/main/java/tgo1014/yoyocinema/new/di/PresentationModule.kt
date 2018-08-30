package tgo1014.yoyocinema.new.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import tgo1014.presentation.MoviesVM

val presentationModule = module {
    viewModel { MoviesVM(searchMovies = get()) }
}