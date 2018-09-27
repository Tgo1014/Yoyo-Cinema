import tgo1014.domain.model.Movie
import tgo1014.presentation.mappers.Mapper
import tgo1014.presentation.model.MovieBinding


object ProductionCountryMapper : Mapper<Movie.ProductionCountry, MovieBinding.ProductionCountry> {

    override fun toPresentation(domain: Movie.ProductionCountry): MovieBinding.ProductionCountry {
        return MovieBinding.ProductionCountry(domain.iso31661, domain.name)
    }

    override fun toDomain(presentation: MovieBinding.ProductionCountry): Movie.ProductionCountry {
        return Movie.ProductionCountry(presentation.iso31661, presentation.name)
    }

}