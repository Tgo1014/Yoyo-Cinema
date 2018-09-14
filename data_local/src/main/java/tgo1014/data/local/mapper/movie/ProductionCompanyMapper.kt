package tgo1014.data.local.mapper.movie

import tgo1014.data.local.mapper.Mapper
import tgo1014.data.local.entities.Movie.ProductionCompany as ProductionCompanyEntity
import tgo1014.domain.model.Movie.ProductionCompany as ProductionCompanyDomain

object ProductionCompanyMapper : Mapper<ProductionCompanyDomain, ProductionCompanyEntity> {

    override fun toEntity(domain: ProductionCompanyDomain): ProductionCompanyEntity {
        return ProductionCompanyEntity(domain.id, domain.logoPath, domain.name, domain.originCountry)
    }

    override fun toDomain(entity: ProductionCompanyEntity): ProductionCompanyDomain {
        return ProductionCompanyDomain(entity.id, entity.logoPath, entity.name, entity.originCountry)
    }

}