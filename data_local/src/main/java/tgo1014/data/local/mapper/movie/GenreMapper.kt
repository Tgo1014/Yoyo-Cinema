package tgo1014.data.local.mapper.movie

import tgo1014.data.local.mapper.Mapper
import tgo1014.domain.model.Movie
import tgo1014.data.local.entities.Movie.Genre as GenreEntity
import tgo1014.domain.model.Movie.Genre as GenreDomain

object GenreMapper : Mapper<GenreDomain, GenreEntity> {

    override fun toEntity(domain: Movie.Genre): tgo1014.data.local.entities.Movie.Genre {
        return GenreEntity(domain.id, domain.name)
    }

    override fun toDomain(entity: tgo1014.data.local.entities.Movie.Genre): Movie.Genre {
        return GenreDomain(entity.id, entity.name)
    }

}