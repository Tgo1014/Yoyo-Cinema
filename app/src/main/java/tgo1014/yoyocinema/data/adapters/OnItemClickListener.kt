package tgo1014.yoyocinema.data.adapters

import android.view.View

interface OnItemClickListener<T> {
    fun onClick(item: T)
}

interface OnMovieItemClicked<T, V : View> {
    fun onMovieIdClicked(item: T, view: V)
}