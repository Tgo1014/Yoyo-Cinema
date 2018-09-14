package tgo1014.data.remote.mappers.searchRequest

import tgo1014.data.remote.mappers.Mapper
import tgo1014.data.remote.model.RemoteMovie
import tgo1014.domain.model.Movie

object GenreMapper : Mapper<RemoteMovie.RemoteGenre, Movie.Genre> {
    override fun parse(remote: RemoteMovie.RemoteGenre): Movie.Genre {
        return Movie.Genre(remote.id, remote.name)
    }
}