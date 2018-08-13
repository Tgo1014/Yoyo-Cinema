package tgo1014.yoyocinema.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_main.*
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.data.adapters.OnItemClickListener
import tgo1014.yoyocinema.data.adapters.OnMovieItemClicked
import tgo1014.yoyocinema.data.adapters.SearchRequestAdapter
import tgo1014.yoyocinema.data.network.requests.SearchRequest
import tgo1014.yoyocinema.helpers.EndlessRecyclerViewScrollListener
import tgo1014.yoyocinema.helpers.extensions.*

class MainActivity : BaseMovieActivity(), OnMovieItemClicked<SearchRequest.Result, View>, OnItemClickListener<Int?> {

    lateinit var adapter: SearchRequestAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configRecycler()
        setListeners()
        setupEditTextBehavior()

        handleViewModel()
    }

    private fun configRecycler() {
        adapter = SearchRequestAdapter(arrayListOf(), this, this)
        layoutManager = LinearLayoutManager(this)
        mainRecyclerMovies.layoutManager = layoutManager
        mainRecyclerMovies.adapter = adapter
    }

    private fun search() {
        hideKeyboard()
        moviesVM.search(mainEdtSearch.toStr())
    }

    private fun setListeners() {
        mainBtnSearch.setOnClickListener { search() }
        mainRecyclerMovies.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(totalItemsCount: Int, view: RecyclerView) {
                moviesVM.loadNextPage()
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

    private fun handleViewModel() {
        moviesVM.observableSearchList.observe(this, Observer {
            it?.let {
                adapter.updateList(it)
            }
        })

        moviesVM.isLoading.observe(this, Observer {
            it?.let { mainProgress.show(it) }
        })

        moviesVM.messagesToDisplay.observe(this, Observer {
            it?.let { showSnack(it) }
        })

        //if we have a configuration change, keeps the recycler visible
        moviesVM.lastSearchTerm.observe(this, Observer {
            if (!it.isNullOrEmpty())
                mainRecyclerMovies.show()
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
        moviesVM.toggleFavorite(favoriteId)
    }

    override fun onClick(item: Int?) {
        onFavoriteClick(item)
    }

    override fun onMovieIdClicked(item: SearchRequest.Result, view: View) {
        MovieDetailsActivity.startWithAnimation(this, item.id, view)
    }
}

