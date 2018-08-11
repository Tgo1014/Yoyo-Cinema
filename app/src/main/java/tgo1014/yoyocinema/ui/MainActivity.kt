package tgo1014.yoyocinema.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import tgo1014.yoyocinema.App
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.data.adapters.MovieAdapter
import tgo1014.yoyocinema.data.adapters.OnItemClickListener
import tgo1014.yoyocinema.data.network.requests.SearchRequest
import tgo1014.yoyocinema.helpers.EndlessRecyclerViewScrollListener
import tgo1014.yoyocinema.helpers.extensions.getViewModel
import tgo1014.yoyocinema.helpers.extensions.toStr
import tgo1014.yoyocinema.viewmodel.MoviesVM


class MainActivity : AppCompatActivity(), OnItemClickListener<SearchRequest.Result> {

    lateinit var moviesVM: MoviesVM
    lateinit var adapter: MovieAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configRecycler()
        setListeners()

        handleViewModel()
    }

    private fun configRecycler() {
        adapter = MovieAdapter(arrayListOf(), this)
        layoutManager = LinearLayoutManager(this)
        mainRecyclerMovies.layoutManager = layoutManager
        mainRecyclerMovies.adapter = adapter
    }

    private fun setListeners() {
        mainBtnSearch.setOnClickListener {
            moviesVM.search(mainEdtSearch.toStr())
            mainRecyclerMovies.visibility = View.VISIBLE
        }
        mainRecyclerMovies.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(totalItemsCount: Int, view: RecyclerView) {
                moviesVM.loadNextPage()
            }
        })
    }

    private fun handleViewModel() {
        moviesVM = getViewModel { MoviesVM(App.appDatabase.moviesDao()) }
        moviesVM.observableSearchList.observe(this, Observer {
            if (it?.isNotEmpty() == true) {
                adapter.updateList(it)
            }
        })
    }

    override fun onClick(item: SearchRequest.Result) {
        MovieDetailsActivity.startActivity(this, item.id)
    }
}

