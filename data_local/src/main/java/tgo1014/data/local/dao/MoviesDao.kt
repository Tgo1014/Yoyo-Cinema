package tgo1014.data.local.dao

import android.database.Observable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import tgo1014.data.local.entities.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flowable<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: Movie) : Long

}