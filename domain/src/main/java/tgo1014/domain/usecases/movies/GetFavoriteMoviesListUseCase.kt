package tgo1014.domain.usecases.movies

import io.reactivex.Observable
import tgo1014.domain.executor.PostExecutionThread
import tgo1014.domain.model.Movie
import tgo1014.domain.repositories.MoviesRepository
import tgo1014.domain.usecases.ObservableUseCase

class GetFavoriteMoviesListUseCase(private val moviesRepository: MoviesRepository,
                                   postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Movie>, Unit>(postExecutionThread) {

    override fun buildUseCase(params: Unit?): Observable<List<Movie>> {
        //return moviesRepository.getFavorites()
        return moviesRepository.getFavorites().map {
            it.sortedBy { it.title }
        }
    }

}