package tgo1014.data.local.mapper.movie

import tgo1014.data.local.mapper.Mapper
import tgo1014.data.local.entities.Movie.ProductionCountry as ProductionCountryEntity
import tgo1014.domain.model.Movie.ProductionCountry as ProductionCountryDomain

object ProductionCountryMapper : Mapper<ProductionCountryDomain, ProductionCountryEntity> {

    override fun toEntity(domain: ProductionCountryDomain): ProductionCountryEntity {
        return ProductionCountryEntity(domain.iso31661, domain.name)
    }

    override fun toDomain(entity: ProductionCountryEntity): ProductionCountryDomain {
        return ProductionCountryDomain(entity.iso31661, entity.name)
    }

}