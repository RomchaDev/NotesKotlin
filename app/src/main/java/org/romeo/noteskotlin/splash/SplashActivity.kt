package org.romeo.noteskotlin.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.romeo.noteskotlin.R
import org.romeo.noteskotlin.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory()
            .create(SplashViewModel::class.java)
    }

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycle.addObserver(viewModel)
    }
}