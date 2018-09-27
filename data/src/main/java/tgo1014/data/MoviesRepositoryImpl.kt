package tgo1014.data

import io.reactivex.Completable
import io.reactivex.Observable
import tgo1014.domain.model.Movie
import tgo1014.domain.model.SearchRequest
import tgo1014.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(private val remoteDataSource: RemoteDataSource,
                           private val localDataSource: LocalDataSource)
    : MoviesRepository {

    override fun favoriteMovie(movieId: Int): Completable {
        return remoteDataSource.getMovieById(movieId).flatMapCompletable {
            localDataSource.favoriteMovie(it)
        }
    }

    override fun unfavoriteMovie(movieId: Int): Completable {
        return Completable.fromAction {
            localDataSource.unfavoriteMovie(movieId)
        }
    }

    override fun search(searchTerm: String, page: Int): Observable<List<SearchRequest.Result>> {
        return remoteDataSource.searchMovies(searchTerm, page)
    }

    override fun getFavorites(): Observable<List<Movie>> {
        return localDataSource.getFavoritesMovies()
    }

    override fun getMovie(movieId: Int): Observable<Movie> {
        return remoteDataSource.getMovieById(movieId)
    }

    override fun add(vararg item: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(vararg item: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(vararg item: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Observable<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}