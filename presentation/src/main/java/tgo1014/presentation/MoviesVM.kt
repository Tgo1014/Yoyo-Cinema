package tgo1014.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tgo1014.domain.usecases.movies.SearchForMovie
import tgo1014.presentation.mappers.SearchRequestResultMapper
import tgo1014.presentation.model.SearchRequestBinding

class MoviesVM(private val searchMovies: SearchForMovie) : ViewModel() {

    private var page = 1
    private var lastPageReached = false
    private var moviesList: MutableList<SearchRequestBinding.ResultBinding> = arrayListOf()

    private val _state: MutableLiveData<Resource<List<SearchRequestBinding.ResultBinding>>> = MutableLiveData()
    val state = _state as LiveData<Resource<List<SearchRequestBinding.ResultBinding>>>

    var lastSearchTerm = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()

    init {
        isLoading.value = false
        lastSearchTerm.value = ""
    }

    fun searchMovie(searchTerm: String?, newSearch: Boolean = false) {
        if (searchTerm.isNullOrEmpty()) {
            //todo create errors enums instead of hardcoding the error
            _state.postValue(Resource.error("To search, type at least one letter"))
            return
        }

        lastSearchTerm.postValue(searchTerm)
        isLoading.postValue(true)

        if (newSearch)
            reset()

        searchMovies.execute(SearchForMovie.Params(searchTerm!!, page),
                { result ->
                    val resultMovies = result.map { SearchRequestResultMapper.toPresentation(it) }

                    if (resultMovies.isEmpty()) {
                        isLoading.postValue(false)
                        lastPageReached = true
                        //TODO create a Exception and throw it so the activity can handle translations
                        _state.postValue(Resource.error("No more movies to load", moviesList))
                        return@execute
                    }

                    //add new movies from the request to the list
                    moviesList.addAll(resultMovies)

                    //set favorites movies flag
                    //TODO movies = setSeachRequestFavorites(movies)

                    _state.postValue(Resource.success(moviesList))
                    isLoading.postValue(false)
                },
                { e ->
                    isLoading.postValue(false)
                    _state.postValue(Resource.error(e.localizedMessage))
                })
    }

    private fun reset() {
        moviesList = arrayListOf()
        lastPageReached = false
        page = 1
    }

    fun loadNextPage() {
        if (!isLoading.value!! && !lastPageReached) {
            page++
            isLoading.postValue(true)
            searchMovie(lastSearchTerm.value)
        }
    }

//    private fun setSeachRequestFavorites(data: MutableList<SearchRequest.Result>): MutableList<SearchRequest.Result> {
//        val favorites = getFavoritesSync()
//        favorites.forEach {
//            data.forEach { searchItem ->
//                searchItem.isFavorite = favorites.any { it.id == searchItem.id }
//            }
//        }
//        return data
//    }

//    fun toggleFavorite(movieId: Int?) {
//        if (movieId == null) return
//        //if it's already a favorite, remove it
//        if (repository.getFavoritesSync().map { it.id }.contains(movieId)) {
//            val movie = repository.getMovieSync(movieId)
//            movie?.let {
//                repository.delete(movie)
//            }
//            return
//        }
//        //else get music and set as a favorite
//        repository.getMovieAndSetAsFavorite(movieId)
//    }

    override fun onCleared() {
        super.onCleared()
        searchMovies.dispose()
    }

}