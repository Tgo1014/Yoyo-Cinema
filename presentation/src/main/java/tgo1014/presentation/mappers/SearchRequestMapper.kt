package tgo1014.presentation.mappers

import tgo1014.domain.model.SearchRequest
import tgo1014.presentation.model.SearchRequestBinding

object SearchRequestMapper : Mapper<SearchRequest, SearchRequestBinding> {

    override fun toPresentation(domain: SearchRequest): SearchRequestBinding {
        return SearchRequestBinding(
                domain.page,
                domain.totalResults,
                domain.totalPages,
                domain.results?.map { SearchRequestResultMapper.toPresentation(it) }
        )
    }

    override fun toDomain(presentation: SearchRequestBinding): SearchRequest {
        return SearchRequest(
                presentation.page,
                presentation.totalResults,
                presentation.totalPages,
                presentation.results?.map {SearchRequestResultMapper.toDomain(it) }
        )
    }

}