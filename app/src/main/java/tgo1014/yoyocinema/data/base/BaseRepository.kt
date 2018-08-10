package tgo1014.yoyocinema.data.base

import androidx.lifecycle.LiveData

interface BaseRepository<T> {
    fun add(vararg item: T)
    fun update(vararg item: T)
    fun delete(vararg item: T)
    fun getAll(): LiveData<List<T>>
}