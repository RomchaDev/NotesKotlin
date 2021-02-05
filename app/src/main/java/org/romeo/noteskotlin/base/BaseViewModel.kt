package org.romeo.noteskotlin.base

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T, VS : BaseViewState<T>> : ViewModel() {
    protected val viewStateLiveData:
            MutableLiveData<VS> = MutableLiveData()

    /**
     * Should contain intent with extra
     * data with key "EXTRA_DATA_KEY" and
     * activity class to start with key
     * "ACTIVITY_TO_START"
     * */
    // Это явно не есть хорошо, но не совсем понимаю, как по-другому
    // передать контроль над запуском activity в ViewModel.
    protected val startActivityLiveData:
            MutableLiveData<Intent> = MutableLiveData()

    fun getViewStateLiveData() =
        viewStateLiveData as LiveData<VS>

    fun getStartActivityLiveData() =
        startActivityLiveData as LiveData<Intent>
}