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
    fun getById(id: Int): LiveData<Movie>

    @Query("SELECT * FROM Movie WHERE :id = id")
    fun getByIdSync(id: Int): Movie?

    @Query("SELECT * FROM Movie WHERE isFavorite = 1")
    fun getFavorites(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie WHERE isFavorite = 1")
    fun getFavoritesSync(): List<Movie>

    @Update
    override fun update(vararg item: Movie)

    @Delete
    override fun delete(vararg item: Movie)

}