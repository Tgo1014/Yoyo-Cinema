package tgo1014.yoyocinema.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorites.*
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.data.adapters.MovieAdapter
import tgo1014.yoyocinema.data.adapters.OnItemClickListener
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.helpers.extensions.show

class FavoritesActivity : BaseMovieActivity(), OnItemClickListener<Movie> {

    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.title = getString(R.string.str_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getFavoritesMovies()
    }

    private fun getFavoritesMovies() {
        moviesVM.getFavorites().observe(this, Observer {
            if (it == null || it.isEmpty()) {
                favoritesTxtMessage.text = getString(R.string.str_no_favorites)
                favoritesTxtMessage.show()
                configRecycler(arrayListOf())
                return@Observer
            }
            configRecycler(it)
        })
    }

    private fun configRecycler(movieList: List<Movie>) {
        adapter = MovieAdapter(movieList.toMutableList(), this, object : OnItemClickListener<Int?> {
            override fun onClick(item: Int?) {
                moviesVM.toggleFavorite(item)
            }
        })
        favoritesRecyclerMovies.layoutManager = LinearLayoutManager(this)
        favoritesRecyclerMovies.adapter = adapter
    }

    override fun onClick(item: Movie) {
        MovieDetailsActivity.startActivity(this, item.id)
    }
}
