package tgo1014.data.remote

import io.reactivex.Observable
import tgo1014.data.RemoteDataSource
import tgo1014.data.remote.mappers.movie.GetMovieByIdMapper
import tgo1014.data.remote.mappers.searchRequest.SearchRequestResultsMapper
import tgo1014.domain.model.Movie
import tgo1014.domain.model.SearchRequest

class RemoteDataSourceImpl(private val moviesService: MoviesService) : RemoteDataSource {

    override fun searchMovies(searchQuery: String, page: Int): Observable<List<SearchRequest.Result>> {
        return moviesService.search(searchQuery, page)
                .map {
                    it.results?.map { SearchRequestResultsMapper.parse(it) }
                }
    }

    override fun getMovieById(movieId: Int): Observable<Movie> {
        return moviesService.getMovieDetails(movieId)
                .map { GetMovieByIdMapper.parse(it) }
    }
}