package tgo1014.domain.usecases.movies

import io.reactivex.Completable
import tgo1014.domain.executor.PostExecutionThread
import tgo1014.domain.model.Movie
import tgo1014.domain.repositories.MoviesRepository
import tgo1014.domain.usecases.CompletableUseCase

class UnfavoriteMovieUseCase(private val moviesRepository: MoviesRepository,
                             postExecutionThread: PostExecutionThread)
    : CompletableUseCase<UnfavoriteMovieUseCase.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Completable {
        if (params == null)
            throw IllegalArgumentException("You must inform the movie id")
        return moviesRepository.unfavoriteMovie(params.movieId)
    }

    data class Params(val movieId: Int)
}