package org.romeo.noteskotlin.create_edit_note

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import org.romeo.noteskotlin.NOTE_KEY
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository

/**
 * If new note needs to be created, than
 * in input intent value 'note' is null,
 * so new note will be created.
 *
 * If existing note needs to be edited,
 * than it will be assigned in the start intent.
 * */
class CreateEditViewModel(startIntent: Intent) : ViewModel(), LifecycleObserver {
    private val note = startIntent.getSerializableExtra(NOTE_KEY) as Note?
    private val isEditModel = note != null
    private var isAlreadyCreated = isEditModel

    var title = note?.title ?: ""
    var content = note?.content ?: ""
    private val noteId = note?.id ?: -1L

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (!isEditModel) {
            if (!isAlreadyCreated && (title.isNotEmpty() || content.isNotEmpty())) {
                isAlreadyCreated = true

                Repository.saveNote(
                    Note(
                        title = title,
                        content = content
                    )
                )
            }
        } else {
            Repository.editNote(
                note?.id, Note(
                    id = noteId,
                    title = title,
                    content = content
                )
            )
        }
    }
}