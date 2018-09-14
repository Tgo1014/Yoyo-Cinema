package tgo1014.data.remote.mappers.movie

import tgo1014.data.remote.mappers.Mapper
import tgo1014.data.remote.model.RemoteMovie
import tgo1014.domain.model.Movie

object ProductionCompanyMapper : Mapper<RemoteMovie.RemoteProductionCompany, Movie.ProductionCompany> {

    override fun parse(remote: RemoteMovie.RemoteProductionCompany): Movie.ProductionCompany {
        return Movie.ProductionCompany(remote.id,
                remote.logoPath,
                remote.name,
                remote.originCountry)
    }

}