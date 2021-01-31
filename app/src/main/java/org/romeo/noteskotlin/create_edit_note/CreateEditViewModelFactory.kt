package org.romeo.noteskotlin.create_edit_note

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateEditViewModelFactory(private val startIntent : Intent) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateEditViewModel(startIntent) as T
    }
}