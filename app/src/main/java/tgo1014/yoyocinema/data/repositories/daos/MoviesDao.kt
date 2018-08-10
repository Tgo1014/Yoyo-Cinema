package tgo1014.yoyocinema.data.repositories.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import tgo1014.yoyocinema.data.base.BaseRepository
import tgo1014.yoyocinema.data.entities.Movie

@Dao
interface MoviesDao : BaseRepository<Movie> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun add(vararg item: Movie)

    @Query("SELECT * FROM Movie")
    override fun getAll(): LiveData<List<Movie>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override fun update(vararg item: Movie)

    @Delete
    override fun delete(vararg item: Movie)

}