package tgo1014.presentation.viewmodels.movies

import MovieMapper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import tgo1014.domain.usecases.movies.FavoriteMovieUseCase
import tgo1014.domain.usecases.movies.GetFavoriteMoviesListUseCase
import tgo1014.domain.usecases.movies.UnfavoriteMovieUseCase
import tgo1014.presentation.Resource
import tgo1014.presentation.model.MovieBinding
import tgo1014.presentation.viewmodels.BaseViewModel

class FavoritesVM(private val favoriteMovieUseCase: FavoriteMovieUseCase,
                  private val unfavoriteMovieUseCase: UnfavoriteMovieUseCase,
                  private val getFavoriteMoviesListUseCase: GetFavoriteMoviesListUseCase)
    : BaseViewModel<List<MovieBinding>>(), LifecycleObserver {

    fun toggleMovieFavorite(movieId: Int) {
        if (_state.value?.data?.any { it.id == movieId } == false) { //if not favorited yet
            favoriteMovieUseCase.execute(
                    FavoriteMovieUseCase.Params(movieId),
                    onComplete = {
                        updateFavoriteList()
                    },
                    onError = {
                        Log.d("LOG_LOG_LOG", "Error")
                    })
            return
        }
        unfavoriteMovieUseCase.execute(UnfavoriteMovieUseCase.Params(movieId),
                onComplete = {
                    updateFavoriteList()
                },
                onError = {
                    Log.d("LOG_LOG_LOG", "Error")
                })
    }

    private fun updateFavoriteList() {
        getFavoriteMoviesListUseCase.execute(
                onNext = {
                    _state.postValue(Resource.success(it.map { MovieMapper.toPresentation(it) }))
                },
                onError = {
                    _state.postValue(Resource.error(it.localizedMessage))
                })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getInitialData() {
        if (_state.value == null)
            updateFavoriteList()
    }

    override fun onCleared() {
        super.onCleared()
        favoriteMovieUseCase.dispose()
        unfavoriteMovieUseCase.dispose()
    }

}

