package tgo1014.yoyocinema.old.data.repositories

import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tgo1014.yoyocinema.old.data.base.BaseRepository
import tgo1014.yoyocinema.old.data.entities.Movie
import tgo1014.yoyocinema.old.data.network.NetworkBoundResource
import tgo1014.yoyocinema.old.data.network.Resource
import tgo1014.yoyocinema.old.data.network.RestClient
import tgo1014.yoyocinema.old.data.network.ResultListener
import tgo1014.yoyocinema.old.data.network.requests.SearchRequest
import tgo1014.yoyocinema.old.data.repositories.daos.MoviesDao

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
                        //unable to remove at moment because we need a context to get the string
                        //no time to handle it right now ¯\_(ツ)_/¯
                        listener.onFailure("No more results")
                        return
                    }
                    listener.onSuccess(response.body()?.results!!)
                    return
                }
                //the same as above
                listener.onFailure("Unable to get results")
            }
        })
    }

    fun getFavorites() = dao.getFavorites()

    fun getFavoritesSync() = runBlocking {
        return@runBlocking async { dao.getFavoritesSync() }.await()
    }

    fun getMovie(movieId: Int): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, Movie>() {
            override fun saveCallResult(item: Movie) = add(item)
            override fun loadFromDb(): LiveData<Movie> = dao.getById(movieId)
            override fun createCall(): Call<Movie> = RestClient.moviesService.getMovieDetails(movieId)
            override fun shouldFetch(data: Movie?) = (data == null)
        }.asLiveData()
    }

    fun getMovieSync(movieId: Int) = runBlocking {
        return@runBlocking async { dao.getByIdSync(movieId) }.await()
    }

    fun getMovieAndSetAsFavorite(favoriteId: Int?) {
        favoriteId?.run {
            RestClient.moviesService.getMovieDetails(favoriteId).enqueue(object : Callback<Movie?> {
                override fun onFailure(call: Call<Movie?>?, t: Throwable?) {
                    //Todo set a message when we fail to get a favorite
                }
                override fun onResponse(call: Call<Movie?>?, response: Response<Movie?>?) {
                    if (response?.isSuccessful == true) {
                        val m = response.body()
                        m?.let {
                            it.isFavorite = true
                            add(it)
                        }
                    }
                }
            })
        }
    }
}