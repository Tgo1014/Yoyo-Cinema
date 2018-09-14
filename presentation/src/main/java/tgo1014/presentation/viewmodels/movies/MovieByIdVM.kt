package tgo1014.presentation.viewmodels.movies

import tgo1014.domain.model.Movie
import tgo1014.domain.usecases.movies.GetMovieByIdUseCase
import tgo1014.presentation.Resource
import tgo1014.presentation.viewmodels.BaseViewModel

class MovieByIdVM(private val getMovieByIdUseCase: GetMovieByIdUseCase) : BaseViewModel<Movie>() {

    fun getMoviesById(movieId: Int) {
        _state.postValue(Resource.loading())
        getMovieByIdUseCase.execute(
                GetMovieByIdUseCase.Params(movieId)
                , { movie -> _state.postValue(Resource.success(movie)) }
                , { e -> _state.postValue(Resource.error(e.localizedMessage)) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        getMovieByIdUseCase.dispose()
    }
}