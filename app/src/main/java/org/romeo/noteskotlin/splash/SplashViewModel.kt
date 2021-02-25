package org.romeo.noteskotlin.splash

import android.accounts.AuthenticatorException
import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.runBlocking
import org.romeo.noteskotlin.base.BaseViewModel
import org.romeo.noteskotlin.model.Repository

class SplashViewModel(repository: Repository) :
    BaseViewModel<Boolean>(repository), LifecycleObserver {

    init {
        runBlocking {
            repository.getCurrentUser()?.let {
                viewStateChannel.send(true)
            } ?: run {
                errorChannel.send(AuthenticatorException())
            }
        }
    }
}