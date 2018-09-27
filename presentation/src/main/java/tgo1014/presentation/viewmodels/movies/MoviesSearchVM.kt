package tgo1014.presentation.viewmodels.movies

import androidx.lifecycle.MutableLiveData
import tgo1014.domain.usecases.movies.SearchForMovieUseCase
import tgo1014.presentation.Resource
import tgo1014.presentation.mappers.SearchRequestResultMapper
import tgo1014.presentation.model.SearchRequestBinding
import tgo1014.presentation.viewmodels.BaseViewModel

class MoviesSearchVM(private val searchMoviesUseCase: SearchForMovieUseCase) : BaseViewModel<List<SearchRequestBinding.ResultBinding>>() {

    private var page = 1
    private var lastPageReached = false
    private var moviesList: MutableList<SearchRequestBinding.ResultBinding> = arrayListOf()

    var lastSearchTerm = MutableLiveData<String>()

    init {
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

        searchMoviesUseCase.execute(SearchForMovieUseCase.Params(searchTerm!!, page),
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

    override fun onCleared() {
        super.onCleared()
        searchMoviesUseCase.dispose()
    }

}