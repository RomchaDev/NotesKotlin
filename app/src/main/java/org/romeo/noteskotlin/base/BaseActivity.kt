package org.romeo.noteskotlin.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.romeo.noteskotlin.R

abstract class BaseActivity<T> : AppCompatActivity() {
    abstract val binding: ViewBinding
    abstract val viewModel: BaseViewModel<T>

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        scope.launch {
            viewModel.viewStateChannel.consumeEach { processData(it) }
            viewModel.errorChannel.consumeEach { processError(it) }
        }

    }

    /**
     * Method to init xml views.
     * can be empty for activities
     * with out views.
     * */
    protected open fun initViews() {
    }

    abstract fun processData(data: T)

    /**
     * If error cached, than shows
     * a toast with default error
     * message by default.
     * */
    protected open fun processError(throwable: Throwable) {
        Toast.makeText(
            this,
            getString(R.string.standart_error),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}