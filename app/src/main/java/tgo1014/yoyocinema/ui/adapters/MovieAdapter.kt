package tgo1014.yoyocinema.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import tgo1014.presentation.model.MovieBinding
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.ui.helpers.Constants
import tgo1014.yoyocinema.ui.OnItemClickListener
import tgo1014.yoyocinema.ui.OnMovieItemClicked
import tgo1014.yoyocinema.ui.extensions.loadUrl

class MovieAdapter(var movieList: MutableList<MovieBinding>,
                   private val listener: OnMovieItemClicked<MovieBinding, View>,
                   private val favoriteListener: OnItemClickListener<Int?>,
                   private val isFavoriteList: Boolean = false)
    : androidx.recyclerview.widget.RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
            MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[holder.adapterPosition]

        //set the movie id as a unique shared transition name
        ViewCompat.setTransitionName(holder.poster, item.id.toString())

        holder.name.text = item.title
        holder.poster.loadUrl(Constants.BASE_IMG_URL + Constants.POSTER_SIZE_342 + item.posterPath, R.drawable.ic_movie_placeholder)
        holder.background.loadUrl(Constants.BASE_IMG_URL + Constants.BACKDROP_SIZE_780 + item.backdropPath, R.drawable.ic_movie_background_placeholder)
        holder.itemView.setOnClickListener { listener.onMovieIdClicked(movieList[holder.adapterPosition], holder.poster) }
        holder.btnFavorite.setOnClickListener {
            favoriteListener.onClick(item.id)
        }

        //Set as yellow if the movie is a favorite
        if (isFavoriteList)
            holder.btnFavorite.setIconTintResource(R.color.colorFavorite)
    }

    fun updateList(list: List<MovieBinding>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(old: Int, new: Int) = movieList[old].id == list[new].id
            override fun getOldListSize() = movieList.size
            override fun getNewListSize() = list.size
            override fun areContentsTheSame(old: Int, new: Int) = movieList[old].id == list[new].id
                   // && movieList[old].isFavorite == list[new].isFavorite
        })
        movieList = list.toMutableList()
        diff.dispatchUpdatesTo(this)
    }

    inner class MovieViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.itemMovieTxtTitle)
        val poster: ImageView = itemView.findViewById(R.id.itemMovieIvPoster)
        val background: ImageView = itemView.findViewById(R.id.itemMovieIvBackground)
        val btnFavorite: com.google.android.material.button.MaterialButton = itemView.findViewById(R.id.itemMovieImgBtnFavorite)
    }
}