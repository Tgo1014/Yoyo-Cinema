package tgo1014.yoyocinema.ui.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tgo1014.presentation.Resource
import tgo1014.presentation.model.MovieBinding
import tgo1014.presentation.viewmodels.movies.FavoritesVM
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.ui.OnItemClickListener
import tgo1014.yoyocinema.ui.OnMovieItemClicked
import tgo1014.yoyocinema.ui.adapters.MovieAdapter
import tgo1014.yoyocinema.ui.extensions.show

class FavoritesActivity : BaseMovieActivity(), OnMovieItemClicked<MovieBinding, View>, OnItemClickListener<Int?> {

    private lateinit var adapter: MovieAdapter
    private val favoritesVM: FavoritesVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.title = getString(R.string.str_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configRecycler(arrayListOf())
        getFavoritesMovies()
    }

    private fun getFavoritesMovies() {
        lifecycle.addObserver(favoritesVM)
        favoritesVM.state.observe(this, Observer {
            when (it.status) {
                Resource.RequestStatus.SUCCESS -> {
                    if (it.data == null || it.data!!.isEmpty()) {
                        favoritesTxtMessage.text = getString(R.string.str_no_favorites)
                        favoritesTxtMessage.show()
                        configRecycler(arrayListOf())
                        return@Observer
                    }
                    adapter.updateList(it.data!!)
                }
            }
        })
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
