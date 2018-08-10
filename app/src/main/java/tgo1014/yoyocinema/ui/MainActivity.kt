package tgo1014.yoyocinema.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import tgo1014.yoyocinema.App
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.helpers.extensions.getViewModel
import tgo1014.yoyocinema.helpers.extensions.toStr
import tgo1014.yoyocinema.viewmodel.MoviesVM

class MainActivity : AppCompatActivity() {

    lateinit var moviesVM: MoviesVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setListeners()
        handleViewModel()

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

        })
    }
}

