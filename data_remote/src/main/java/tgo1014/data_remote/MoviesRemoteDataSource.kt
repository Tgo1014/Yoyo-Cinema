package tgo1014.data_remote

import io.reactivex.Observable
import tgo1014.data.RemoteDataSource
import tgo1014.data_remote.mappers.SearchRequestResultsMapper
import tgo1014.data_remote.service.MoviesService
import tgo1014.domain.model.SearchRequest

class MoviesRemoteDataSource(private val moviesService: MoviesService) : RemoteDataSource {

    override fun searchMovies(searchQuery: String, page: Int): Observable<List<SearchRequest.Result>> {
        return moviesService.search(searchQuery, page)
                .map { it.results?.map { SearchRequestResultsMapper.parse(it) } }
    }
}