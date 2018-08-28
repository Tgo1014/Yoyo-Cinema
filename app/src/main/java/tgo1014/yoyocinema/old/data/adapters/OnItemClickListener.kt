package tgo1014.yoyocinema.old.data.adapters

import android.view.View

interface OnItemClickListener<T> {
    fun onClick(item: T)
}

interface OnMovieItemClicked<T, V : View> {
    fun onMovieIdClicked(item: T, view: V)
}