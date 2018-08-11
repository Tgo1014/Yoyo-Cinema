package tgo1014.yoyocinema.helpers.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

fun <T> T.toJson(): String {
    return Gson().toJson(this, object : TypeToken<T>() {}.type)
}

fun <T> String.fromJson(): T {
    return GsonBuilder().create().fromJson<T>(this, object : TypeToken<T>() {}.type)
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(crossinline factory: () -> T): T {
    @Suppress("UNCHECKED_CAST")
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}

fun TextView.toStr() = this.text.toString()

fun ImageView.loadUrl(url: String?, @DrawableRes placeholder: Int? = null) {
    if (placeholder == null) {
        Picasso.with(context)
                .load(url)
                .into(this)
        return
    }
    Picasso.with(context)
            .load(url)
            .placeholder(placeholder)
            .into(this)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
