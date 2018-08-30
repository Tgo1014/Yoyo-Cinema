package tgo1014.yoyocinema.new.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import tgo1014.presentation.model.SearchRequestBinding
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.new.ui.OnItemClickListener
import tgo1014.yoyocinema.new.ui.OnMovieItemClicked
import tgo1014.yoyocinema.old.Constants
import tgo1014.yoyocinema.old.data.network.requests.SearchRequest
import tgo1014.yoyocinema.old.helpers.extensions.loadUrl

class SearchRequestAdapter(var searchList: MutableList<SearchRequestBinding.ResultBinding>,
                           private val listener: OnMovieItemClicked<SearchRequestBinding.ResultBinding, View>,
                           private val favoriteListener: OnItemClickListener<Int?>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<SearchRequestAdapter.MovieViewHolder>() {

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
            //item.isFavorite = !item.isFavorite
         //   handleFavoriteStarColor(holder.btnFavorite, item.isFavorite)
            favoriteListener.onClick(item.id)
        }
       // handleFavoriteStarColor(holder.btnFavorite, item.isFavorite)
    }

    private fun handleFavoriteStarColor(button: com.google.android.material.button.MaterialButton, isFavorite: Boolean) {
        val iconTint = if (isFavorite) R.color.colorFavorite else android.R.color.white
        button.setIconTintResource(iconTint)
    }

    fun updateList(list: List<SearchRequestBinding.ResultBinding>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(old: Int, new: Int) = searchList[old].id == list[new].id
            override fun getOldListSize() = searchList.size
            override fun getNewListSize() = list.size
            override fun areContentsTheSame(old: Int, new: Int) = searchList[old].id == list[new].id
                   // && searchList[old].isFavorite == list[new].isFavorite
        })
        searchList = list.toMutableList()
        diff.dispatchUpdatesTo(this)
    }

    inner class MovieViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.itemMovieTxtTitle)
        val poster: ImageView = itemView.findViewById(R.id.itemMovieIvPoster)
        val background: ImageView = itemView.findViewById(R.id.itemMovieIvBackground)
        val btnFavorite: com.google.android.material.button.MaterialButton = itemView.findViewById(R.id.itemMovieImgBtnFavorite)
    }
}