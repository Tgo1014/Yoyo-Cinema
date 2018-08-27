package tgo1014.presentation.mappers

import tgo1014.domain.model.SearchRequest
import tgo1014.presentation.model.SearchRequestBinding

object SearchRequestResultMapper : Mapper<SearchRequest.Result, SearchRequestBinding.ResultBinding> {
    override fun toPresentation(domain: SearchRequest.Result): SearchRequestBinding.ResultBinding {
        return SearchRequestBinding.ResultBinding(
                domain.voteCount,
                domain.id,
                domain.video,
                domain.voteAverage,
                domain.title,
                domain.popularity,
                domain.posterPath,
                domain.originalLanguage,
                domain.originalTitle,
                domain.genreIds,
                domain.backdropPath,
                domain.adult,
                domain.overview,
                domain.releaseDate
        )
    }

    override fun toDomain(presentation: SearchRequestBinding.ResultBinding): SearchRequest.Result {
        return SearchRequest.Result(
                presentation.voteCount,
                presentation.id,
                presentation.video,
                presentation.voteAverage,
                presentation.title,
                presentation.popularity,
                presentation.posterPath,
                presentation.originalLanguage,
                presentation.originalTitle,
                presentation.genreIds,
                presentation.backdropPath,
                presentation.adult,
                presentation.overview,
                presentation.releaseDate
        )
    }
}