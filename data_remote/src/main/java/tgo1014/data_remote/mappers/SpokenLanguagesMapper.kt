package tgo1014.data_remote.mappers

import tgo1014.data_remote.model.RemoteMovie
import tgo1014.domain.model.Movie

object SpokenLanguagesMapper : Mapper<RemoteMovie.RemoteSpokenLanguage, Movie.SpokenLanguage> {
    override fun parse(remote: RemoteMovie.RemoteSpokenLanguage): Movie.SpokenLanguage {
        return Movie.SpokenLanguage(remote.iso6391, remote.name)
    }
}