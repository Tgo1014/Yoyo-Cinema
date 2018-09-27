import tgo1014.domain.model.Movie
import tgo1014.presentation.mappers.Mapper
import tgo1014.presentation.model.MovieBinding

object ProductionCompanyMapper : Mapper<Movie.ProductionCompany, MovieBinding.ProductionCompany> {

    override fun toPresentation(domain: Movie.ProductionCompany): MovieBinding.ProductionCompany {
        return MovieBinding.ProductionCompany(domain.id, domain.logoPath, domain.name, domain.originCountry)
    }

    override fun toDomain(presentation: MovieBinding.ProductionCompany): Movie.ProductionCompany {
        return Movie.ProductionCompany(presentation.id, presentation.logoPath, presentation.name, presentation.originCountry)
    }

}