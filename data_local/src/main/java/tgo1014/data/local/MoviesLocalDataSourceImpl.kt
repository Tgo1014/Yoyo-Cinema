package tgo1014.data.local

import android.content.Context
import android.util.Log
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.Observable
import tgo1014.data.LocalDataSource
import tgo1014.data.local.db.MoviesDatabase
import tgo1014.data.local.mapper.movie.MovieMapper
import tgo1014.domain.model.Movie


class MoviesLocalDataSourceImpl(context: Context, allowMainThreadQueries: Boolean = false) : LocalDataSource {

    private val database: MoviesDatabase by lazy {

        val builder = Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "moviesdb"
        )

        if (allowMainThreadQueries)
            builder.allowMainThreadQueries()

        builder.build()

    }

    private val moviesDao = database.moviesDao()

    override fun getMovieById(movieId: Int): Observable<Movie> {
        return moviesDao.getMovieById(movieId).map {
            MovieMapper.toDomain(it)
        }.toObservable()
    }

    override fun getFavoritesMovies(): Observable<List<Movie>> {
        Log.d("LOG_LOG_LOG", "GOT MOVIES LIST")

        return moviesDao.getFavoriteMovies()
                .map {
                    it.map { movie -> MovieMapper.toDomain(movie) }
                }.toObservable()
    }

    override fun favoriteMovie(movie: Movie): Completable {
        return Completable.fromAction {
            moviesDao.add(MovieMapper.toEntity(movie))
            Log.d("LOG_LOG_LOG", "ADDED")
        }
    }

    override fun unfavoriteMovie(movieId: Int): Completable {
        return Completable.fromAction {
            moviesDao.remove(movieId)
            Log.d("LOG_LOG_LOG", "REMOVED")
        }
    }

}