package org.romeo.noteskotlin.splash

import android.accounts.AuthenticatorException
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import org.koin.android.viewmodel.ext.android.viewModel
import org.romeo.noteskotlin.base.BaseActivity
import org.romeo.noteskotlin.databinding.ActivitySplashBinding
import org.romeo.noteskotlin.main.MainActivity

class SplashActivity : BaseActivity<Boolean>() {
    override val viewModel: SplashViewModel by viewModel()

    override val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun startAuthActivity() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                        AuthUI.IdpConfig.EmailBuilder().build()
                    )
                ).build(),
            SING_IN_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SING_IN_REQUEST_CODE)
            if (resultCode == RESULT_OK)
                startMainActivity()
            else
                startAuthActivity()
    }

    companion object {
        private const val SING_IN_REQUEST_CODE = 1
    }

    override fun processData(data: Boolean) {
        takeIf { !data }?.startAuthActivity()?: startMainActivity()
    }

    override fun processError(throwable: Throwable) {
        takeIf { throwable is AuthenticatorException }?.startAuthActivity()
    }
}