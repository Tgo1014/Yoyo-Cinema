package tgo1014.data.local.mapper

interface Mapper<D, E> {
    fun toEntity(domain: D): E
    fun toDomain(entity: E): D
}