import tgo1014.domain.model.Movie
import tgo1014.presentation.mappers.Mapper
import tgo1014.presentation.model.MovieBinding

object GenreMapper : Mapper<Movie.Genre, MovieBinding.Genre> {

    override fun toPresentation(domain: Movie.Genre): MovieBinding.Genre {
        return MovieBinding.Genre(domain.id, domain.name)
    }

    override fun toDomain(presentation: MovieBinding.Genre): Movie.Genre {
        return Movie.Genre(presentation.id, presentation.name)
    }

}