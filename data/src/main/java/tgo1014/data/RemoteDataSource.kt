package tgo1014.data

import io.reactivex.Observable
import tgo1014.domain.model.Movie
import tgo1014.domain.model.SearchRequest

interface RemoteDataSource {
    fun searchMovies(searchQuery: String, page: Int): Observable<List<SearchRequest.Result>>
    fun getMovieById(movieId: Int): Observable<Movie>
}