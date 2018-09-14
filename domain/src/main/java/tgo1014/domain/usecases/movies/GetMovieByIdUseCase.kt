package tgo1014.domain.usecases.movies

import io.reactivex.Observable
import tgo1014.domain.executor.PostExecutionThread
import tgo1014.domain.model.Movie
import tgo1014.domain.repositories.MoviesRepository
import tgo1014.domain.usecases.ObservableUseCase

class GetMovieByIdUseCase(private val moviesRepository: MoviesRepository,
                          postExecutionThread: PostExecutionThread)
    : ObservableUseCase<Movie, GetMovieByIdUseCase.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Observable<Movie> {
        if (params == null)
            throw IllegalArgumentException("You must inform the movie id")
        return moviesRepository.getMovie(params.movieId)
    }

    data class Params(val movieId: Int)
}