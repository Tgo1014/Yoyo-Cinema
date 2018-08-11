package tgo1014.yoyocinema.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import tgo1014.yoyocinema.App
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.data.adapters.MovieAdapter
import tgo1014.yoyocinema.data.adapters.OnItemClickListener
import tgo1014.yoyocinema.data.network.requests.SearchRequest
import tgo1014.yoyocinema.helpers.extensions.getViewModel
import tgo1014.yoyocinema.helpers.extensions.toStr
import tgo1014.yoyocinema.viewmodel.MoviesVM

class MainActivity : AppCompatActivity(), OnItemClickListener<SearchRequest.Result> {

    lateinit var moviesVM: MoviesVM
    lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
        configRecycler()

        handleViewModel()
    }

    private fun configRecycler() {
        adapter = MovieAdapter(arrayListOf(), this)
        mainRecyclerMovies.layoutManager = LinearLayoutManager(this)
        mainRecyclerMovies.adapter = adapter
    }

    private fun setListeners() {
        mainBtnSearch.setOnClickListener {
            moviesVM.search(mainEdtSearch.toStr())
            mainRecyclerMovies.visibility = View.VISIBLE
        }
    }

    private fun handleViewModel() {
        moviesVM = getViewModel { MoviesVM(App.appDatabase.moviesDao()) }
        moviesVM.observableSearchList.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.updateList(it)
            }
        })
    }

    override fun onClick(): SearchRequest.Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

