package org.romeo.noteskotlin.splash

import android.accounts.AuthenticatorException
import androidx.lifecycle.LifecycleObserver
import org.romeo.noteskotlin.base.BaseViewModel
import org.romeo.noteskotlin.model.Repository

class SplashViewModel : BaseViewModel<Boolean, SplashViewState>(), LifecycleObserver {

    init {
        Repository.getCurrentUserLiveData().observeForever { user ->
            user?.let {
                viewStateLiveData.value = SplashViewState(true, null)
            } ?: run {
                viewStateLiveData.value =
                    SplashViewState(false, AuthenticatorException())
            }
        }
    }
}