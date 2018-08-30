package tgo1014.yoyocinema.new.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import tgo1014.presentation.Resource
import tgo1014.presentation.model.SearchRequestBinding
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.new.ui.OnItemClickListener
import tgo1014.yoyocinema.new.ui.OnMovieItemClicked
import tgo1014.yoyocinema.new.ui.adapters.SearchRequestAdapter
import tgo1014.yoyocinema.old.helpers.EndlessRecyclerViewScrollListener
import tgo1014.yoyocinema.old.helpers.extensions.hideKeyboard
import tgo1014.yoyocinema.old.helpers.extensions.show
import tgo1014.yoyocinema.old.helpers.extensions.showSnack
import tgo1014.yoyocinema.old.helpers.extensions.toStr

class MainActivity :
        BaseMovieActivity(),
        OnMovieItemClicked<SearchRequestBinding.ResultBinding, View>,
        OnItemClickListener<Int?> {

    lateinit var adapter: SearchRequestAdapter
    lateinit var layoutManager: LinearLayoutManager

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
        moviesVM.searchMovie(mainEdtSearch.toStr(), true)
    }

    private fun setListeners() {
        mainBtnSearch.setOnClickListener { search() }
        mainRecyclerMovies.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(totalItemsCount: Int, view: androidx.recyclerview.widget.RecyclerView) {
                moviesVM.loadNextPage()
            }
        })
        mainRecyclerMovies.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
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

        moviesVM.isLoading.observe(this, Observer {
            it?.let { mainProgress.show(it) }
        })

        //if we have a configuration change, keeps the recycler visible
        moviesVM.lastSearchTerm.observe(this, Observer {
            if (!it.isNullOrEmpty())
                mainRecyclerMovies.show()
        })

        moviesVM.state.observe(this, Observer {
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

    private fun onFavoriteClick(favoriteId: Int?) {
        //moviesVM.toggleFavorite(favoriteId)
    }

    override fun onClick(item: Int?) {
        onFavoriteClick(item)
    }

    override fun onMovieIdClicked(item: SearchRequestBinding.ResultBinding, view: View) {
        MovieDetailsActivity.startWithAnimation(this, item.id, view)
    }
}

