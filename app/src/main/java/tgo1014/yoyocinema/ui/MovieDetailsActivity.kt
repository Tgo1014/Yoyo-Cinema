package tgo1014.yoyocinema.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
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
        supportPostponeEnterTransition()

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        if (movieId == -1) {
            toast(getString(R.string.str_invalid_movieid))
            finish()
            return
        }

        val imageTransitionName = intent.getStringExtra(EXTRA_MOVIE_TRANSITION_NAME)
        detailActivityIvPoster.transitionName = imageTransitionName
        supportStartPostponedEnterTransition()

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
                    toast(getString(R.string.str_unable_to_load_movie))
                    detailActivityProgress.gone()
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
        private const val EXTRA_MOVIE_TRANSITION_NAME = "EXTRA_MOVIE_TRANSITION_NAME"

        fun startActivity(context: Context, movieId: Int?) {
            val i = Intent(context, MovieDetailsActivity::class.java)
            i.putExtra(EXTRA_MOVIE_ID, movieId)
            context.startActivity(i)
        }

        fun startWithAnimation(activity: Activity, movieId: Int?, sharedImageView: View) {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            intent.putExtra(EXTRA_MOVIE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView))

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    sharedImageView,
                    ViewCompat.getTransitionName(sharedImageView)!!)

            activity.startActivity(intent, options.toBundle())
        }
    }
}
