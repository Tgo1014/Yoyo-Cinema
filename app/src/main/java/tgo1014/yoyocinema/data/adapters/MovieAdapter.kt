package tgo1014.yoyocinema.data.adapters


import android.support.design.button.MaterialButton
import android.support.v4.view.ViewCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import tgo1014.yoyocinema.Constants
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.helpers.extensions.loadUrl

class MovieAdapter(var movieList: MutableList<Movie>,
                   private val listener: OnMovieItemClicked<Movie, View>,
                   private val favoriteListener: OnItemClickListener<Int?>,
                   private val isFavoriteList: Boolean = false)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
            MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[holder.adapterPosition]

        //shared transition
        ViewCompat.setTransitionName(holder.poster, item.id.toString())

        holder.name.text = item.title
        holder.poster.loadUrl(Constants.BASE_IMG_URL + Constants.POSTER_SIZE_342 + item.posterPath, R.drawable.ic_movie_placeholder)
        holder.background.loadUrl(Constants.BASE_IMG_URL + Constants.BACKDROP_SIZE_780 + item.backdropPath, R.drawable.ic_movie_background_placeholder)
        holder.itemView.setOnClickListener { listener.onMovieIdClicked(movieList[holder.adapterPosition], holder.poster) }
        holder.btnFavorite.setOnClickListener {
            favoriteListener.onClick(item.id)
        }
        if (isFavoriteList)
            holder.btnFavorite.setIconTintResource(R.color.colorFavorite)
    }

    fun updateList(list: List<Movie>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(old: Int, new: Int) = movieList[old].id == list[new].id
            override fun getOldListSize() = movieList.size
            override fun getNewListSize() = list.size
            override fun areContentsTheSame(old: Int, new: Int) = movieList[old].id == list[new].id
                    && movieList[old].isFavorite == list[new].isFavorite
        })
        movieList = list.toMutableList()
        diff.dispatchUpdatesTo(this)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.itemMovieTxtTitle)
        val poster: ImageView = itemView.findViewById(R.id.itemMovieIvPoster)
        val background: ImageView = itemView.findViewById(R.id.itemMovieIvBackground)
        val btnFavorite: MaterialButton = itemView.findViewById(R.id.itemMovieImgBtnFavorite)
    }
}