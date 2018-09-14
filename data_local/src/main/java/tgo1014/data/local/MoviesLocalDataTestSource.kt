package tgo1014.data.local

import io.reactivex.Completable
import io.reactivex.Observable
import tgo1014.data.LocalDataSource
import tgo1014.domain.model.Movie

/**
 * This class is used just for graph test purposes in the PersistenceTestModule.kt
 */
class MoviesLocalDataTestSource : LocalDataSource {
    override fun getFavoritesMovies(): Observable<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMovieById(movieId: Int): Observable<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun favoriteMovie(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unfavoriteMovie(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}