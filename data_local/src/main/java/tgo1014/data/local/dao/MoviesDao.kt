package tgo1014.data.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import tgo1014.data.local.entities.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: Movie): Long

    @Delete
    fun remove(movie: Movie)

    @Query("DELETE FROM Movie WHERE id = :movieId")
    fun remove(movieId: Int)

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flowable<Movie>

    @Query("SELECT * FROM Movie WHERE favorite = 1")
    fun getFavoriteMovies(): Flowable<List<Movie>>

}