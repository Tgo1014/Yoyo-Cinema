package tgo1014.data.remote.mappers

import tgo1014.data.remote.model.RemoteSearchRequest
import tgo1014.domain.model.SearchRequest

object SearchRequestResultsMapper : Mapper<RemoteSearchRequest.Result, SearchRequest.Result> {
    override fun parse(remote: RemoteSearchRequest.Result): SearchRequest.Result {
        return SearchRequest.Result(
                remote.voteCount,
                remote.id,
                remote.video,
                remote.voteAverage,
                remote.title,
                remote.popularity,
                remote.posterPath,
                remote.originalLanguage,
                remote.originalTitle,
                remote.genreIds,
                remote.backdropPath,
                remote.adult,
                remote.overview,
                remote.releaseDate
        )
    }
}