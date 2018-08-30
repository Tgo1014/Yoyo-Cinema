package tgo1014.yoyocinema.old.helpers.extensions

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

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

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}


fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showSnack(text: String, lenght: Int = com.google.android.material.snackbar.Snackbar.LENGTH_SHORT) {
    com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), text, lenght).show()
}

infix fun Int.dpToPixel(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return this * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}
