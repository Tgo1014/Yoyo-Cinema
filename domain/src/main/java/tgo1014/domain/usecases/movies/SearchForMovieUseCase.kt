package tgo1014.domain.usecases.movies

import io.reactivex.Observable
import tgo1014.domain.executor.PostExecutionThread
import tgo1014.domain.model.SearchRequest
import tgo1014.domain.repositories.MoviesRepository
import tgo1014.domain.usecases.ObservableUseCase

class SearchForMovieUseCase(private val moviesRepository: MoviesRepository,
                            postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<SearchRequest.Result>, SearchForMovieUseCase.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Observable<List<SearchRequest.Result>> {
        if (params == null)
            throw IllegalArgumentException("You must inform the search query")
        return moviesRepository.search(params.searchQuery, params.page)
    }

    data class Params(val searchQuery: String, val page: Int)
}