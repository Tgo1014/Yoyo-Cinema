package tgo1014.yoyocinema.ui.activities

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

abstract class BaseMovieActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}