package tgo1014.data.local

import android.content.Context
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.Observable
import tgo1014.data.LocalDataSource
import tgo1014.data.local.db.MoviesDatabase
import tgo1014.data.local.mapper.movie.MovieMapper
import tgo1014.domain.model.Movie


class MoviesLocalDataSource(context: Context, allowMainThreadQueries: Boolean = false) : LocalDataSource {

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun favoriteMovie(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unfavoriteMovie(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}