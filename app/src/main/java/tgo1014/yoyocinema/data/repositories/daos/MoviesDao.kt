package tgo1014.yoyocinema.data.repositories.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import tgo1014.yoyocinema.data.base.BaseRepository
import tgo1014.yoyocinema.data.entities.Movie

@Dao
interface MoviesDao : BaseRepository<Movie> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun add(vararg item: Movie)

    @Query("SELECT * FROM Movie")
    override fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie WHERE :id = id")
    fun getById(id: Int) : LiveData<Movie>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override fun update(vararg item: Movie)

    @Delete
    override fun delete(vararg item: Movie)

}