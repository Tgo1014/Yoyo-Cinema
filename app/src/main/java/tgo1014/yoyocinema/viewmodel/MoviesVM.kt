package tgo1014.yoyocinema.viewmodel

import androidx.lifecycle.ViewModel
import tgo1014.yoyocinema.data.repositories.MoviesRepository
import tgo1014.yoyocinema.data.repositories.daos.MoviesDao

class MoviesVM(moviesDao: MoviesDao) : ViewModel() {
    val repository: MoviesRepository = MoviesRepository(moviesDao)
}