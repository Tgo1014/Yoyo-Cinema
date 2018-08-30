package tgo1014.yoyocinema.new.ui.activities

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tgo1014.presentation.MoviesVM

abstract class BaseMovieActivity : AppCompatActivity() {

    val moviesVM: MoviesVM by viewModel()

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