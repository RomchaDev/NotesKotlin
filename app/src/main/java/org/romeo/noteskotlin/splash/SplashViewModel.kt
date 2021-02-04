package org.romeo.noteskotlin.splash

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import org.romeo.noteskotlin.model.Repository

class SplashViewModel : ViewModel(), LifecycleObserver {
    private val userLiveData = MutableLiveData<FirebaseUser?>()

    init {
        Repository.getCurrentUserLiveData().observeForever { user ->
            userLiveData.value = user
        }
    }

    fun getUserLiveData(): LiveData<FirebaseUser?> = userLiveData

    companion object {
        fun getAuthStartIntent() = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                    AuthUI.IdpConfig.EmailBuilder().build()
                )
            ).build()
    }
}