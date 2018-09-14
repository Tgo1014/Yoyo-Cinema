package tgo1014.data.remote.mappers.searchRequest

import tgo1014.data.remote.mappers.Mapper
import tgo1014.data.remote.model.RemoteMovie
import tgo1014.domain.model.Movie

object SpokenLanguagesMapper : Mapper<RemoteMovie.RemoteSpokenLanguage, Movie.SpokenLanguage> {
    override fun parse(remote: RemoteMovie.RemoteSpokenLanguage): Movie.SpokenLanguage {
        return Movie.SpokenLanguage(remote.iso6391, remote.name)
    }
}