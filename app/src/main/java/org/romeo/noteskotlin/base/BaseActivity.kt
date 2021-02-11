package org.romeo.noteskotlin.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import org.romeo.noteskotlin.ACTIVITY_TO_START
import org.romeo.noteskotlin.NOTE_KEY
import org.romeo.noteskotlin.R

abstract class BaseActivity<T, VS : BaseViewState<T>> : AppCompatActivity() {
    abstract val binding: ViewBinding
    abstract val viewModel: BaseViewModel<T, VS>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        viewModel.getStartActivityLiveData().observe(this) { inIntent ->
            val classJava = inIntent.getSerializableExtra(ACTIVITY_TO_START) as Class<*>

            val startIntent = Intent(this, classJava)

            startIntent.putExtra(NOTE_KEY, inIntent.getSerializableExtra(NOTE_KEY))

            startActivity(startIntent)

        }

        viewModel.getViewStateLiveData().observe(this) { viewState ->
            viewState.data?.let { processData(it) }
                ?: viewState.error?.let { processError(it) }
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
    protected open fun processError(error: Throwable) {
        Toast.makeText(
            this,
            getString(R.string.standart_error),
            Toast.LENGTH_LONG
        ).show()
    }
}