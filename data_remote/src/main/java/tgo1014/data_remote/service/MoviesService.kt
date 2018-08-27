package tgo1014.data_remote.service

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tgo1014.data_remote.model.RemoteMovie
import tgo1014.data_remote.model.RemoteSearchRequest

interface MoviesService {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: Int): Call<RemoteMovie>

    @GET("search/movie")
    fun search(
            @Query("query") searchQuery: String,
            @Query("page") page: Int): Observable<RemoteSearchRequest>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}