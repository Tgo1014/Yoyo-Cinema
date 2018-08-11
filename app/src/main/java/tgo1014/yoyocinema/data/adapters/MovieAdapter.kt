package tgo1014.yoyocinema.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tgo1014.yoyocinema.Constants
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.data.network.requests.SearchRequest
import tgo1014.yoyocinema.helpers.extensions.loadUrl

class MovieAdapter(var movieList: MutableList<SearchRequest.Result>,
                   private val listener: OnItemClickListener<SearchRequest.Result>)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
            MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[holder.adapterPosition]
        holder.name.text = item.title
        holder.poster.loadUrl(Constants.BASE_IMG_URL + Constants.POSTER_SIZE_342 + item.posterPath)
        holder.background.loadUrl(Constants.BASE_IMG_URL + Constants.BACKDROP_SIZE_300 + item.backdropPath)
    }

    fun updateList(list: List<SearchRequest.Result>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(old: Int, new: Int) = movieList[old].id == list[new].id
            override fun getOldListSize() = movieList.size
            override fun getNewListSize() = list.size
            override fun areContentsTheSame(old: Int, new: Int) = movieList[old].id == list[new].id
        })
        movieList = list.toMutableList()
        diff.dispatchUpdatesTo(this)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.itemMovieTxtTitle)
        val poster: ImageView = itemView.findViewById(R.id.itemMovieIvPoster)
        val background: ImageView = itemView.findViewById(R.id.itemMovieIvBackground)

    }
}