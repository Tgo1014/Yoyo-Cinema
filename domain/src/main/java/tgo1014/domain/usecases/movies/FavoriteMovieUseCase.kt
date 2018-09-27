package tgo1014.domain.usecases.movies

import io.reactivex.Completable
import tgo1014.domain.executor.PostExecutionThread
import tgo1014.domain.repositories.MoviesRepository
import tgo1014.domain.usecases.CompletableUseCase

class FavoriteMovieUseCase(private val moviesRepository: MoviesRepository,
                           postExecutionThread: PostExecutionThread)
    : CompletableUseCase<FavoriteMovieUseCase.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Completable {
        if (params == null)
            throw IllegalArgumentException("You must inform the movie id")
        return moviesRepository.favoriteMovie(params.movieId)
    }

    data class Params(val movieId: Int)
}