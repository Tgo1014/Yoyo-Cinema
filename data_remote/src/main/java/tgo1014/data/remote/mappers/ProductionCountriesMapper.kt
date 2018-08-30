package tgo1014.data.remote.mappers

import tgo1014.data.remote.model.RemoteMovie
import tgo1014.domain.model.Movie

object ProductionCountriesMapper : Mapper<RemoteMovie.RemoteProductionCountry, Movie.ProductionCountry> {
    override fun parse(remote: RemoteMovie.RemoteProductionCountry): Movie.ProductionCountry {
        return Movie.ProductionCountry(remote.iso31661, remote.name)
    }
}