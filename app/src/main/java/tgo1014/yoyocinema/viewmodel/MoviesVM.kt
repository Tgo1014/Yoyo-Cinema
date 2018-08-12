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
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    private var lastPageReached = false
    var lastSearchTerm: String = ""

    val observableSearchList = MutableLiveData<MutableList<SearchRequest.Result>>()

    fun search(searchTerm: String, loadMoreSearch: Boolean = false) {
        if (!loadMoreSearch)
            reset(searchTerm)

        isLoading.value = true
        repository.search(searchTerm, page, object : ResultListener<List<SearchRequest.Result>> {
            override fun onSucess(data: List<SearchRequest.Result>) {
                //get current movie list
                var movies = observableSearchList.value ?: arrayListOf()
                //add new movies from the request to the list
                movies.addAll(data)
                //set favorites movies flag
                movies = setSeachRequestFavorites(movies)
                //notify the livedata with the new movies
                observableSearchList.value = movies
                isLoading.value = false
            }

            override fun onFailure(message: String) {
                isLoading.value = false
            }
        })
    }

    private fun setSeachRequestFavorites(data: MutableList<SearchRequest.Result>): MutableList<SearchRequest.Result> {
        val favorites = getFavoritesSync()
        favorites.forEach {
            data.forEach { searchItem ->
                searchItem.isFavorite = favorites.any { it.id == searchItem.id }
            }
        }
        return data
    }

    private fun reset(searchTerm: String) {
        lastSearchTerm = searchTerm
        observableSearchList.value = arrayListOf()
        isLoading.value = false
        lastPageReached = false
        page = 1
    }

    fun getMovie(movieId: Int) = repository.getMovie(movieId)

    fun getFavorites() = repository.getFavorites()

    fun getFavoritesSync() = repository.getFavoritesSync()

    fun loadNextPage() {
        if (!isLoading.value!! && !lastPageReached) {
            page++
            isLoading.value = true
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