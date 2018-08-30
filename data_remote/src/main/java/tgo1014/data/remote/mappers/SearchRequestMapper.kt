package tgo1014.data.remote.mappers

import tgo1014.data.remote.model.RemoteSearchRequest
import tgo1014.domain.model.SearchRequest

object SearchRequestMapper : Mapper<RemoteSearchRequest, SearchRequest> {
    override fun parse(remote: RemoteSearchRequest): SearchRequest {
        return SearchRequest(
                remote.page,
                remote.totalResults,
                remote.totalPages,
                remote.results?.map { SearchRequestResultsMapper.parse(it) }
        )
    }
//    override fun parse(remote: RemoteMovie): Movie {
//        return Movie(
//                remote.id,
//                remote.adult,
//                remote.backdropPath,
//                remote.budget,
//                remote.remoteGenres?.map { GenreMapper.parse(it) },
//                remote.homepage,
//                remote.imdbId,
//                remote.originalLanguage, remote.originalTitle,
//                remote.overview,
//                remote.popularity,
//                remote.posterPath,
//                remote.productionCompanies?.map { ProductionCompanyMapper.parse(it) },
//                remote.remoteProductionCountries?.map { ProductionCountriesMapper.parse(it) },
//                remote.releaseDate,
//                remote.revenue,
//                remote.runtime,
//                remote.remoteSpokenLanguages?.map { SpokenLanguagesMapper.parse(it) },
//                remote.status,
//                remote.tagline,
//                remote.title,
//                remote.video,
//                remote.voteAverage,
//                remote.voteCount
//        )
//    }
}