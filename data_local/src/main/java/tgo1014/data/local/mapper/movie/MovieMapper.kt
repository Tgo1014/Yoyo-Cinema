package tgo1014.data.local.mapper.movie

import tgo1014.data.local.mapper.Mapper
import tgo1014.data.local.entities.Movie as MovieEntity
import tgo1014.domain.model.Movie as MovieDomain

object MovieMapper : Mapper<MovieDomain, MovieEntity> {

    override fun toEntity(domain: MovieDomain): MovieEntity {
        return MovieEntity(
                domain.id,
                domain.adult,
                domain.backdropPath,
                domain.budget,
                domain.genres?.map { GenreMapper.toEntity(it) },
                domain.homepage,
                domain.imdbId,
                domain.originalLanguage,
                domain.originalTitle,
                domain.overview,
                domain.popularity,
                domain.posterPath,
                domain.productionCompanies?.map { ProductionCompanyMapper.toEntity(it) },
                domain.productionCountries?.map { ProductionCountryMapper.toEntity(it) },
                domain.releaseDate,
                domain.revenue,
                domain.runtime,
                domain.spokenLanguages?.map { SpokenLanguageMapper.toEntity(it) },
                domain.status,
                domain.tagline,
                domain.title,
                domain.video,
                domain.voteAverage,
                domain.voteCount,
                true //is favorite if it's in the db
        )
    }

    override fun toDomain(entity: MovieEntity): MovieDomain {
        return MovieDomain(
                entity.id,
                entity.adult,
                entity.backdropPath,
                entity.budget,
                entity.genres?.map { GenreMapper.toDomain(it) },
                entity.homepage,
                entity.imdbId,
                entity.originalLanguage,
                entity.originalTitle,
                entity.overview,
                entity.popularity,
                entity.posterPath,
                entity.productionCompanies?.map { ProductionCompanyMapper.toDomain(it) },
                entity.productionCountries?.map { ProductionCountryMapper.toDomain(it) },
                entity.releaseDate,
                entity.revenue,
                entity.runtime,
                entity.spokenLanguages?.map { SpokenLanguageMapper.toDomain(it) },
                entity.status,
                entity.tagline,
                entity.title,
                entity.video,
                entity.voteAverage,
                entity.voteCount
        )
    }

}