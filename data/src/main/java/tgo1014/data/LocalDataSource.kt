package tgo1014.data

import io.reactivex.Completable
import io.reactivex.Observable
import tgo1014.domain.model.Movie

interface LocalDataSource {
    fun getFavoritesMovies(): Observable<List<Movie>>
    fun getMovieById(movieId: Int): Observable<Movie>
    fun favoriteMovie(movie: Movie): Completable
    fun unfavoriteMovie(movieId: Int): Completable
}