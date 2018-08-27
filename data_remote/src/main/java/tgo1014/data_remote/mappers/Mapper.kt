package tgo1014.data_remote.mappers

interface Mapper<in R, out M> {
    fun parse(remote: R): M
}