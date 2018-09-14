package tgo1014.data.local.mapper.movie

import tgo1014.data.local.mapper.Mapper
import tgo1014.data.local.entities.Movie.SpokenLanguage as SpokenLanguageEntity
import tgo1014.domain.model.Movie.SpokenLanguage as SpokenLanguageDomain

object SpokenLanguageMapper : Mapper<SpokenLanguageDomain, SpokenLanguageEntity> {

    override fun toEntity(domain: SpokenLanguageDomain): SpokenLanguageEntity {
        return SpokenLanguageEntity(domain.iso6391, domain.name)
    }

    override fun toDomain(entity: SpokenLanguageEntity): SpokenLanguageDomain {
        return SpokenLanguageDomain(entity.iso6391, entity.name)
    }

}