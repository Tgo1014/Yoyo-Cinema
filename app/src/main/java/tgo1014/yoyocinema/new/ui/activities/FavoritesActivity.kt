package tgo1014.yoyocinema.new.ui.activities

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_favorites.*
import tgo1014.presentation.model.MovieBinding
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.new.ui.OnItemClickListener
import tgo1014.yoyocinema.new.ui.OnMovieItemClicked
import tgo1014.yoyocinema.new.ui.adapters.MovieAdapter

class FavoritesActivity : BaseMovieActivity(), OnMovieItemClicked<MovieBinding, View>, OnItemClickListener<Int?> {

    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.title = getString(R.string.str_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getFavoritesMovies()
        configRecycler(arrayListOf())
    }

    private fun getFavoritesMovies() {
//        moviesVM.getFavorites().observe(this, Observer {
//            if (it == null || it.isEmpty()) {
//                favoritesTxtMessage.text = getString(R.string.str_no_favorites)
//                favoritesTxtMessage.show()
//                configRecycler(arrayListOf())
//                return@Observer
//            }
//            adapter.updateList(it)
//        })
    }

    private fun configRecycler(movieList: List<MovieBinding>) {
        adapter = MovieAdapter(movieList.toMutableList(), this, this, true)
        favoritesRecyclerMovies.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        favoritesRecyclerMovies.adapter = adapter
    }

    override fun onClick(item: Int?) {
        //moviesVM.toggleFavorite(item)
    }

    override fun onMovieIdClicked(item: MovieBinding, view: View) {
        MovieDetailsActivity.startWithAnimation(this, item.id, view)
    }
}
