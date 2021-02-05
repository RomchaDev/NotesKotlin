package org.romeo.noteskotlin.create_edit_note

import android.content.Intent
import org.romeo.noteskotlin.base.BaseViewModel
import org.romeo.noteskotlin.DEFAULT_NOTE_ID_VALUE
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
class CreateEditViewModel(startIntent: Intent) :
    BaseViewModel<Note?, CreateEditViewState>() {

    private val note = startIntent.getSerializableExtra(NOTE_KEY) as Note?

    /**
     * Bound with two-way data binding.
     * */
    var title = note?.title ?: ""
    var content = note?.content ?: ""


    private var noteId = note?.id ?: DEFAULT_NOTE_ID_VALUE

    fun saveCurrentNote() {
        noteId = Repository.saveNote(
            Note(
                id = noteId,
                title = title,
                content = content
            )
        )

    }
}