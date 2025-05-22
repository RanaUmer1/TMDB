import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: AppUtils
 *
 * @version 1.0
 */



object AppUtils {
    fun formatVoteCount(count: Int): String {
        return when {
            count >= 1_000_000 -> String.format("%.1fM votes", count / 1_000_000f)
            count >= 1_000 -> String.format("%.1fK votes", count / 1_000f)
            else -> "$count votes"
        }
    }

}
