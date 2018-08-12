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
import tgo1014.yoyocinema.data.adapters.SearchRequestAdapter
import tgo1014.yoyocinema.data.network.requests.SearchRequest
import tgo1014.yoyocinema.helpers.EndlessRecyclerViewScrollListener
import tgo1014.yoyocinema.helpers.extensions.toStr

class MainActivity : BaseMovieActivity(), OnItemClickListener<SearchRequest.Result> {

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
        adapter = SearchRequestAdapter(arrayListOf(), this, object : OnItemClickListener<Int?> {
            override fun onClick(item: Int?) {
                onFavoriteClick(item)
            }
        })
        layoutManager = LinearLayoutManager(this)
        mainRecyclerMovies.layoutManager = layoutManager
        mainRecyclerMovies.adapter = adapter
    }

    private fun setListeners() {
        mainBtnSearch.setOnClickListener {
            moviesVM.search(mainEdtSearch.toStr())
        }
        mainRecyclerMovies.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(totalItemsCount: Int, view: RecyclerView) {
                moviesVM.loadNextPage()
            }
        })
        mainRecyclerMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
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
            if (it?.isNotEmpty() == true) {
                adapter.updateList(it)
            }
        })
        if (moviesVM.lastSearchTerm.isNotEmpty())
            mainRecyclerMovies.visibility = View.VISIBLE

    }

    private fun setupEditTextBehavior() {
        mainEdtSearch.setOnEditorActionListener { _, actionId, code ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || ((code.action == KeyEvent.ACTION_DOWN) && (code.keyCode == KeyEvent.KEYCODE_ENTER))) {
                moviesVM.search(mainEdtSearch.toStr())
            }
            true
        }
    }

    override fun onClick(item: SearchRequest.Result) {
        MovieDetailsActivity.startActivity(this, item.id)
    }

    private fun onFavoriteClick(favoriteId: Int?) {
        moviesVM.toggleFavorite(favoriteId)
    }
}

