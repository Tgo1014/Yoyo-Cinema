package tgo1014.yoyocinema.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import tgo1014.yoyocinema.data.network.ResultListener
import tgo1014.yoyocinema.data.network.requests.SearchRequest
import tgo1014.yoyocinema.data.repositories.MoviesRepository
import tgo1014.yoyocinema.data.repositories.daos.MoviesDao

class MoviesVM(moviesDao: MoviesDao) : ViewModel() {

    private val repository: MoviesRepository = MoviesRepository(moviesDao)
    private var page = 1
    var isLoading = false
    private var lastPageReached = false
    private lateinit var lastSearchTerm: String

    val observableSearchList = MediatorLiveData<MutableList<SearchRequest.Result>>()

    fun search(searchTerm: String) {
        lastSearchTerm = searchTerm
        repository.search(searchTerm, page, object : ResultListener<List<SearchRequest.Result>> {
            override fun onSucess(data: List<SearchRequest.Result>) {
                val movies = observableSearchList.value
                movies?.addAll(data)
                observableSearchList.value = movies
            }

            override fun onFailure(message: String) {

            }
        })
    }

    fun loadNextPage() {
        if (!isLoading && !lastPageReached) {
            page++
            search(lastSearchTerm)
        }
    }

}