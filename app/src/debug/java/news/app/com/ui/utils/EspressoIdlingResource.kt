package news.app.com.ui.utils

import androidx.test.espresso.idling.CountingIdlingResource
import timber.log.Timber

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        Timber.d("increment()")
        countingIdlingResource.dumpStateToLogs()
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.dumpStateToLogs()
        if (!countingIdlingResource.isIdleNow) {
            Timber.d("decrement()")
            countingIdlingResource.decrement()
        }
    }
}