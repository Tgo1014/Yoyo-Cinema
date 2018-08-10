package tgo1014.yoyocinema.data.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.launch
import tgo1014.yoyocinema.data.base.BaseRepository
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.data.repositories.daos.MoviesDao

class MoviesRepository(val dao: MoviesDao) : BaseRepository<Movie> {
    override fun getAll(): LiveData<List<Movie>> = dao.getAll()

    override fun add(vararg item: Movie) {
        launch { dao.add(*item) }
    }

    override fun update(vararg item: Movie) {
        launch { dao.update(*item) }
    }

    override fun delete(vararg item: Movie) {
        launch { dao.delete(*item) }
    }
}