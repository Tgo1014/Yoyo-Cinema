package tgo1014.data

import io.reactivex.Observable
import tgo1014.domain.model.SearchRequest

interface RemoteDataSource {
    fun searchMovies(searchQuery: String, page: Int) : Observable<List<SearchRequest.Result>>
}