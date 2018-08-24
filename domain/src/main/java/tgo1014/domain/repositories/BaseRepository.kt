package tgo1014.domain.repositories

import io.reactivex.Observable

interface BaseRepository<T> {
    fun add(vararg item: T)
    fun update(vararg item: T)
    fun delete(vararg item: T)
    fun getAll(): Observable<List<T>>
}