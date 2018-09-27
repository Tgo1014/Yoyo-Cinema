package tgo1014.yoyocinema.ui.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun View.show(show: Boolean = true) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.invisible(invisible: Boolean = true) {
    visibility = if (!invisible) View.VISIBLE else View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.toggle() {
    this.show(this.visibility != View.VISIBLE)
}

fun TextView.toStr() = this.text.toString()

fun ImageView.loadUrl(url: String?, @DrawableRes placeholder: Int? = null) {
    if (placeholder == null) {
        Glide.with(context)
                .load(url)
                .into(this)
        return
    }
    Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions().placeholder(placeholder))
            .load(url)
            .into(this)
}