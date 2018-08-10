package tgo1014.yoyocinema.data.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tgo1014.yoyocinema.data.base.BaseRepository
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.data.network.RestClient
import tgo1014.yoyocinema.data.network.ResultListener
import tgo1014.yoyocinema.data.network.requests.SearchRequest
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

    fun search(searchTerm: String, page: Int, listener: ResultListener<List<SearchRequest.Result>>) {
        RestClient.moviesService.search(searchTerm, page).enqueue(object : Callback<SearchRequest> {
            override fun onFailure(call: Call<SearchRequest>, t: Throwable) {
                listener.onFailure(t.localizedMessage)
            }

            override fun onResponse(call: Call<SearchRequest>, response: Response<SearchRequest>) {
                if (response.isSuccessful) {
                    if (response.body()?.results?.isEmpty() == true) {
                        listener.onFailure("No more results")
                        return
                    }
                    listener.onSucess(response.body()?.results!!)
                    return
                }
                listener.onFailure("Unable to get results")
            }
        })
    }
}