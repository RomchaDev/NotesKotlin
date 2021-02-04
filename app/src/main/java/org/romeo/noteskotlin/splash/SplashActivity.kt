package org.romeo.noteskotlin.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import org.romeo.noteskotlin.R
import org.romeo.noteskotlin.USER_KEY
import org.romeo.noteskotlin.databinding.ActivitySplashBinding
import org.romeo.noteskotlin.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private var currentUser: FirebaseUser? = null
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

        viewModel.getUserLiveData().observe(this) { user ->
            user?.let {
                currentUser = it
                startMainActivity()
            } ?: startAuthActivity()
        }
    }

    private fun startMainActivity() {
        currentUser.let { user ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(USER_KEY, user)
            startActivity(intent)
        }
    }

    private fun startAuthActivity() {
        startActivityForResult(
            SplashViewModel.getAuthStartIntent(),
            SING_IN_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SING_IN_REQUEST_CODE)

            if (resultCode == RESULT_OK) {
                startMainActivity()
            } else startAuthActivity()
    }

    companion object {
        private const val SING_IN_REQUEST_CODE = 1
    }
}