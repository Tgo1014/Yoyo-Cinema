package tgo1014.yoyocinema.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import tgo1014.yoyocinema.data.network.ResultListener
import tgo1014.yoyocinema.data.network.requests.SearchRequest
import tgo1014.yoyocinema.data.repositories.MoviesRepository
import tgo1014.yoyocinema.data.repositories.daos.MoviesDao

class MoviesVM(moviesDao: MoviesDao) : ViewModel() {

    private val repository: MoviesRepository = MoviesRepository(moviesDao)
    private var page = 1
    var isLoading = false
    private var lastPageReached = false
    var lastSearchTerm: String = ""

    val observableSearchList = MutableLiveData<MutableList<SearchRequest.Result>>()

    fun search(searchTerm: String, loadMoreSearch: Boolean = false) {
        if (!loadMoreSearch)
            reset(searchTerm)
        repository.search(searchTerm, page, object : ResultListener<List<SearchRequest.Result>> {
            override fun onSucess(data: List<SearchRequest.Result>) {
                val movies = observableSearchList.value ?: arrayListOf()
                movies.addAll(data)
                observableSearchList.value = movies
                isLoading = false
            }

            override fun onFailure(message: String) {
                isLoading = false
            }
        })
    }

    private fun reset(searchTerm: String) {
        lastSearchTerm = searchTerm
        observableSearchList.value = arrayListOf()
        isLoading = false
        lastPageReached = false
        page = 1
    }

    fun getMovie(movieId: Int) = repository.getMovie(movieId)

    fun getFavorites() = repository.getFavorites()

    fun getFavoritesSync() = repository.getFavorites()

    fun loadNextPage() {
        if (!isLoading && !lastPageReached) {
            page++
            isLoading = true
            search(lastSearchTerm, true)
        }
    }

    fun toggleFavorite(movieId: Int?) {
        if (movieId == null) return
        //if it's already a favorite, remove it
        if (repository.getFavoritesSync().map { it.id }.contains(movieId)) {
            val movie = repository.getMovieSync(movieId)
            movie?.let {
                repository.delete(movie)
            }
            return
        }
        //else get music and set as a favorite
        repository.getMovieAndSetAsFavorite(movieId)
    }
}