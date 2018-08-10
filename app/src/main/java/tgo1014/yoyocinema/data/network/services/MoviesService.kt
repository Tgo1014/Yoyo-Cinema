package tgo1014.yoyocinema.data.network.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.data.network.requests.SearchRequest

interface MoviesService {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: Int): Call<Movie>

    @GET("search/movie")
    fun search(@Path("query") searchQuery: String): Call<SearchRequest>
}