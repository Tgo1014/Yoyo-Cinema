package tgo1014.domain.repositories

import io.reactivex.Observable
import tgo1014.domain.model.Movie
import tgo1014.domain.model.SearchRequest

interface MoviesRepository : BaseRepository<Movie> {
    fun search(searchTerm: String, page: Int): Observable<SearchRequest.Result>
    fun getFavorites(): Observable<List<Movie>>
    fun getMovie(movieId: Int): Observable<Movie>
    fun getMovieAndSetAsFavorite(favoriteId: Int?)
}