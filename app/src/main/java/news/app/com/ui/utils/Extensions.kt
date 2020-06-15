package news.app.com.ui.utils
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun FragmentManager.hasFragments(): Boolean = fragments.isNotEmpty()

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.inTransaction{replace(frameId, fragment)}
}

fun Context.toast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.getCurrentLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales[0]
    } else {
        resources.configuration.locale
    }
}

fun Locale.getUTCDateTimeFormatter(): SimpleDateFormat{
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", this)
}

fun toVisibility(constraint: Boolean): Int = if (constraint) {
    View.VISIBLE
} else {
    View.GONE
}