package tgo1014.yoyocinema.helpers.extensions

import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

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
        Picasso.get()
                .load(url)
                .into(this)
        return
    }
    Picasso.get()
            .load(url)
            .placeholder(placeholder)
            .into(this)
}