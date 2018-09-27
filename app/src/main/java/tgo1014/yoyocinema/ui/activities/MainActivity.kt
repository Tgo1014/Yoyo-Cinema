package tgo1014.yoyocinema.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tgo1014.presentation.Resource
import tgo1014.presentation.model.SearchRequestBinding
import tgo1014.presentation.viewmodels.movies.FavoritesVM
import tgo1014.presentation.viewmodels.movies.MoviesSearchVM
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.ui.helpers.EndlessRecyclerViewScrollListener
import tgo1014.yoyocinema.ui.OnItemClickListener
import tgo1014.yoyocinema.ui.OnMovieItemClicked
import tgo1014.yoyocinema.ui.adapters.SearchRequestAdapter
import tgo1014.yoyocinema.ui.extensions.hideKeyboard
import tgo1014.yoyocinema.ui.extensions.show
import tgo1014.yoyocinema.ui.extensions.showSnack
import tgo1014.yoyocinema.ui.extensions.toStr

class MainActivity :
        BaseMovieActivity(),
        OnMovieItemClicked<SearchRequestBinding.ResultBinding, View>,
        OnItemClickListener<Int?> {

    private lateinit var adapter: SearchRequestAdapter
    lateinit var layoutManager: LinearLayoutManager
    private val moviesSearchVM: MoviesSearchVM by viewModel()
    private val favoritesVM: FavoritesVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        configRecycler()
        setListeners()
        setupEditTextBehavior()

        handleViewModel()
    }

    private fun configRecycler() {
        adapter = SearchRequestAdapter(arrayListOf(), this, this)
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        mainRecyclerMovies.layoutManager = layoutManager
        mainRecyclerMovies.adapter = adapter
    }

    private fun search() {
        hideKeyboard()
        moviesSearchVM.searchMovie(mainEdtSearch.toStr(), true)
    }

    private fun setListeners() {
        mainBtnSearch.setOnClickListener { search() }
        mainRecyclerMovies.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(totalItemsCount: Int, view: RecyclerView) {
                moviesSearchVM.loadNextPage()
            }
        })
        mainRecyclerMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //handle the scroll direction to show or hide the fab
                when {
                    dy > 0 -> mainFabFavorites.hide()
                    dy < 0 -> mainFabFavorites.show()
                }
            }
        })
        mainFabFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //do nothing, otherwise the popcorn icon will leave the app
        return true
    }

    private fun handleViewModel() {

        moviesSearchVM.isLoading.observe(this, Observer {
            it?.let { mainProgress.show(it) }
        })

        //if we have a configuration change, keeps the recycler visible
        moviesSearchVM.lastSearchTerm.observe(this, Observer {
            if (!it.isNullOrEmpty())
                mainRecyclerMovies.show()
        })

        moviesSearchVM.state.observe(this, Observer {
            when (it.status) {
                Resource.RequestStatus.LOADING -> mainProgress.show()
                Resource.RequestStatus.SUCCESS -> {
                    it.data?.let { adapter.updateList(it) }
                }
                Resource.RequestStatus.ERROR -> {
                    it.data?.let { adapter.updateList(it) }
                    it.message?.let { showSnack(it) }
                }
            }
        })

        favoritesVM.state.observe(this, Observer {
            when (it.status) {
                Resource.RequestStatus.LOADING -> Unit
                Resource.RequestStatus.SUCCESS -> {
                    it.data?.let {
                        adapter.updateFavoriteList(it.toMutableList())
                    }
                }
                Resource.RequestStatus.ERROR -> Unit
            }
        })

    }

    private fun setupEditTextBehavior() {
        mainEdtSearch.setOnEditorActionListener { _, actionId, code ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    ((code.action == KeyEvent.ACTION_DOWN) && (code.keyCode == KeyEvent.KEYCODE_ENTER))) {
                search()
            }
            true
        }
    }

    private fun onFavoriteClick(movieId: Int?) {
        movieId?.let {
            favoritesVM.toggleMovieFavorite(movieId)
        }
    }

    override fun onClick(item: Int?) {
        onFavoriteClick(item)
    }

    override fun onMovieIdClicked(item: SearchRequestBinding.ResultBinding, view: View) {
        MovieDetailsActivity.startWithAnimation(this, item.id, view)
    }
}

