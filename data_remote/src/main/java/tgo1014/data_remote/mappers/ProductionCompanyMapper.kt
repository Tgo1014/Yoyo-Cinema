package tgo1014.data_remote.mappers

import tgo1014.data_remote.model.RemoteMovie
import tgo1014.domain.model.Movie

object ProductionCompanyMapper : Mapper<RemoteMovie.RemoteProductionCompany, Movie.ProductionCompany> {
    override fun parse(remote: RemoteMovie.RemoteProductionCompany): Movie.ProductionCompany {
        return Movie.ProductionCompany(remote.id,
                remote.logoPath,
                remote.name,
                remote.originCountry)
    }
}