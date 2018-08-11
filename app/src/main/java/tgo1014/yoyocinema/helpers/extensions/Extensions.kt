package tgo1014.yoyocinema.helpers.extensions

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

fun <T> T.toJson(): String {
    return Gson().toJson(this, object : TypeToken<T>() {}.type)
}

fun <T> String.fromJson(): T {
    return Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(crossinline factory: () -> T): T {
    @Suppress("UNCHECKED_CAST")
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}

fun TextView.toStr() = this.text.toString()

fun ImageView.loadUrl(url: String?) {
    Picasso.with(context)
            .load(url)
            .into(this)
}
