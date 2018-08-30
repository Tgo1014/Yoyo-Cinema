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
    private val _state: MutableLiveData<Resource<List<SearchRequestBinding.ResultBinding>>> = MutableLiveData()
    val state = _state as LiveData<Resource<List<SearchRequestBinding.ResultBinding>>>

    fun searchMovie(searchTerm: String?) {
        if (searchTerm.isNullOrEmpty()) {
            //todo create errors enums instead of hardcoding the error
            _state.postValue(Resource.error("To search, type at least one letter"))
            return
        }
        _state.postValue(Resource.loading())
        searchMovies.execute(SearchForMovie.Params(searchTerm!!, page),
                { result ->
                    val value = result.map { SearchRequestResultMapper.toPresentation(it) }
                    _state.postValue(Resource.success(value))
                },
                { e -> _state.postValue(Resource.error(e.localizedMessage)) })
    }

    override fun onCleared() {
        super.onCleared()
        searchMovies.dispose()
    }

}