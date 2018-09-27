import tgo1014.domain.model.Movie
import tgo1014.presentation.mappers.Mapper
import tgo1014.presentation.model.MovieBinding

object SpokenLanguageMapper : Mapper<Movie.SpokenLanguage, MovieBinding.SpokenLanguage> {

    override fun toPresentation(domain: Movie.SpokenLanguage): MovieBinding.SpokenLanguage {
        return MovieBinding.SpokenLanguage(domain.iso6391, domain.name)
    }

    override fun toDomain(presentation: MovieBinding.SpokenLanguage): Movie.SpokenLanguage {
        return Movie.SpokenLanguage(presentation.iso6391, presentation.name)
    }

}