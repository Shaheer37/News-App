package news.app.com.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import news.app.com.R
import news.app.com.ui.EventObserver
import news.app.com.ui.injection.ViewModelFactory
import news.app.com.ui.news.viewnews.NewsViewModel
import news.app.com.ui.utils.getCurrentLocale
import news.app.com.ui.utils.getUTCDateFormatter
import news.app.com.ui.utils.getUTCDateTimeFormatter
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import java.util.*
import java.util.jar.Manifest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object{
        const val PERMISSION_INT = 101
    }

    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
                viewModelStore,
                viewModelFactory
        ).get(MainViewModel::class.java)

        val formatter = getCurrentLocale().getUTCDateFormatter()

        Timber.d(formatter.format(Date()))
    }
}
