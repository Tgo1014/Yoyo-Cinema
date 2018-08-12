package tgo1014.yoyocinema.ui

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import kotlinx.android.synthetic.main.activity_movie_details.*
import tgo1014.yoyocinema.Constants
import tgo1014.yoyocinema.R
import tgo1014.yoyocinema.data.entities.Movie
import tgo1014.yoyocinema.data.network.RequestStatus
import tgo1014.yoyocinema.helpers.extensions.gone
import tgo1014.yoyocinema.helpers.extensions.loadUrl
import tgo1014.yoyocinema.helpers.extensions.toast

class MovieDetailsActivity : BaseMovieActivity() {

    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        if (movieId == -1) {
            toast("Invalid movieId")
            finish()
            return
        }
        handleViewModel()
    }

    private fun handleViewModel() {
        moviesVM.getMovie(movieId).observe(this, Observer {
            when (it?.status) {
                RequestStatus.LOADING -> Unit
                RequestStatus.SUCCESS -> {
                    fillMovieData(it.data)
                    detailActivityProgress.gone()
                }
                RequestStatus.ERROR -> {
                    toast("Unable to load movie at moment")
                }
            }
        })
    }

    private fun fillMovieData(movie: Movie?) {
        movie?.let {
            supportActionBar?.title = movie.title

            detailActivityIvPoster.loadUrl(Constants.BASE_IMG_URL + Constants.POSTER_SIZE_500 + movie.posterPath, R.drawable.ic_movie_placeholder)
            detailActivityIvHeader.loadUrl(Constants.BASE_IMG_URL + Constants.BACKDROP_SIZE_1280 + movie.backdropPath, R.drawable.ic_movie_background_placeholder)
            detailActivityTvTitle.text = movie.title
            detailActivityTagline.text = movie.tagline
            detailActivityTvOverviewContent.text = movie.overview
            detailActivityTvReleaseDateContent.text = movie.releaseDate
            detailActivityTvStatusContent.text = movie.status

            when {
                movie.homepage != null -> {
                    detailActivityTvHomepageContent.text = Html.fromHtml("<a href=\"" + movie.homepage + "\">" + movie.homepage + "</a>")
                    detailActivityTvHomepageContent.movementMethod = LinkMovementMethod.getInstance()
                }
                else -> detailActivityTvHomepageContent.text = "-"
            }
        }
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun startActivity(context: Context, movieId: Int?) {
            val i = Intent(context, MovieDetailsActivity::class.java)
            i.putExtra(EXTRA_MOVIE_ID, movieId)
            context.startActivity(i)
        }
    }
}
