import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: Extensions
 *
 * @version 1.0
 */

fun Activity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

