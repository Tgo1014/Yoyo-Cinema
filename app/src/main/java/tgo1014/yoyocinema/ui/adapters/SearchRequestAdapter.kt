package tgo1014.yoyocinema.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tgo1014.presentation.model.MovieBinding
import tgo1014.presentation.model.SearchRequestBinding
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.ui.OnItemClickListener
import tgo1014.yoyocinema.ui.OnMovieItemClicked
import tgo1014.yoyocinema.ui.helpers.Constants
import tgo1014.yoyocinema.ui.extensions.loadUrl

class SearchRequestAdapter(var searchList: MutableList<SearchRequestBinding.ResultBinding>,
                           private val listener: OnMovieItemClicked<SearchRequestBinding.ResultBinding, View>,
                           private val favoriteListener: OnItemClickListener<Int?>)
    : RecyclerView.Adapter<SearchRequestAdapter.MovieViewHolder>() {

    private var favoriteList: MutableList<MovieBinding> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
            MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun getItemCount() = searchList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = searchList[holder.adapterPosition]

        //set the movie id as a unique shared transition name
        ViewCompat.setTransitionName(holder.poster, item.id.toString())

        holder.name.text = item.title
        holder.poster.loadUrl(Constants.BASE_IMG_URL + Constants.POSTER_SIZE_342 + item.posterPath, R.drawable.ic_movie_placeholder)
        holder.background.loadUrl(Constants.BASE_IMG_URL + Constants.BACKDROP_SIZE_780 + item.backdropPath, R.drawable.ic_movie_background_placeholder)
        holder.itemView.setOnClickListener { listener.onMovieIdClicked(searchList[holder.adapterPosition], holder.poster) }
        holder.btnFavorite.setOnClickListener {
            favoriteListener.onClick(item.id)
        }
        handleFavoriteStarColor(holder.btnFavorite, favoriteList.any { item.id == it.id })
    }

    private fun handleFavoriteStarColor(button: com.google.android.material.button.MaterialButton, isFavorite: Boolean) {
        val iconTint = if (isFavorite) R.color.colorFavorite else android.R.color.white
        button.setIconTintResource(iconTint)
    }

    /**
     * The normalUpdate flag determines if the contents are the same
     *
     * Setting it to false force the update
     */
    fun updateList(list: List<SearchRequestBinding.ResultBinding>, normalUpdate: Boolean = true) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(old: Int, new: Int) = searchList[old].id == list[new].id
            override fun getOldListSize() = searchList.size
            override fun getNewListSize() = list.size
            override fun areContentsTheSame(old: Int, new: Int) = searchList[old].id == list[new].id
                    && normalUpdate
        })
        searchList = list.toMutableList()
        diff.dispatchUpdatesTo(this)
    }

    fun updateFavoriteList(list: MutableList<MovieBinding>) {
        favoriteList = list
        updateList(searchList, false) //forces the list update
    }

    inner class MovieViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.itemMovieTxtTitle)
        val poster: ImageView = itemView.findViewById(R.id.itemMovieIvPoster)
        val background: ImageView = itemView.findViewById(R.id.itemMovieIvBackground)
        val btnFavorite: com.google.android.material.button.MaterialButton = itemView.findViewById(R.id.itemMovieImgBtnFavorite)
    }
}