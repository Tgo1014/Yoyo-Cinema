package tgo1014.yoyocinema.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import tgo1014.yoyocinema.App
import tgo1014.yoyocinema.helpers.extensions.getViewModel
import tgo1014.yoyocinema.viewmodel.MoviesVM

abstract class BaseMovieActivity : AppCompatActivity() {

    lateinit var moviesVM: MoviesVM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesVM = getViewModel { MoviesVM(App.appDatabase.moviesDao()) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}