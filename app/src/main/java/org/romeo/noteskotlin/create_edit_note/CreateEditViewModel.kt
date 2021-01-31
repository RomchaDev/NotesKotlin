package org.romeo.noteskotlin.create_edit_note

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository

class CreateEditViewModel : ViewModel(), LifecycleObserver {
    private var isAlreadyCreated = false

    var title = ""
    var content = ""

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (!isAlreadyCreated && (title.isNotEmpty() || content.isNotEmpty())) {
            isAlreadyCreated = true

            Repository.addNewNote(
                Note(
                    title = title,
                    content = content
                )
            )
        }
    }
}