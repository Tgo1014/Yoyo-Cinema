
import tgo1014.domain.model.Movie
import tgo1014.presentation.mappers.Mapper
import tgo1014.presentation.model.MovieBinding

object MovieMapper : Mapper<Movie, MovieBinding> {

    override fun toPresentation(domain: Movie): MovieBinding {
        return MovieBinding(
                domain.id,
                domain.adult,
                domain.backdropPath,
                domain.budget,
                domain.genres?.map { GenreMapper.toPresentation(it) },
                domain.homepage,
                domain.imdbId,
                domain.originalLanguage,
                domain.originalTitle,
                domain.overview,
                domain.popularity,
                domain.posterPath,
                domain.productionCompanies?.map { ProductionCompanyMapper.toPresentation(it) },
                domain.productionCountries?.map { ProductionCountryMapper.toPresentation(it) },
                domain.releaseDate,
                domain.revenue,
                domain.runtime,
                domain.spokenLanguages?.map { SpokenLanguageMapper.toPresentation(it) },
                domain.status,
                domain.tagline,
                domain.title,
                domain.video,
                domain.voteAverage,
                domain.voteCount
        )
    }

    override fun toDomain(presentation: MovieBinding): Movie {
        return Movie(
                presentation.id,
                presentation.adult,
                presentation.backdropPath,
                presentation.budget,
                presentation.genres?.map { GenreMapper.toDomain(it) },
                presentation.homepage,
                presentation.imdbId,
                presentation.originalLanguage,
                presentation.originalTitle,
                presentation.overview,
                presentation.popularity,
                presentation.posterPath,
                presentation.productionCompanies?.map { ProductionCompanyMapper.toDomain(it) },
                presentation.productionCountries?.map { ProductionCountryMapper.toDomain(it) },
                presentation.releaseDate,
                presentation.revenue,
                presentation.runtime,
                presentation.spokenLanguages?.map { SpokenLanguageMapper.toDomain(it) },
                presentation.status,
                presentation.tagline,
                presentation.title,
                presentation.video,
                presentation.voteAverage,
                presentation.voteCount
        )
    }

}