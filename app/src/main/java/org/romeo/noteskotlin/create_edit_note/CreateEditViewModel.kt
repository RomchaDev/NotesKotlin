package org.romeo.noteskotlin.create_edit_note

import android.content.Intent
import androidx.annotation.ColorInt
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.romeo.noteskotlin.DEFAULT_NOTE_COLOR
import org.romeo.noteskotlin.base.BaseViewModel
import org.romeo.noteskotlin.DEFAULT_NOTE_ID_VALUE
import org.romeo.noteskotlin.NOTE_KEY
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository

/**
 * New note can be created, or old one
 * can be edited here.
 * */
class CreateEditViewModel(startIntent: Intent, repository: Repository) :
    BaseViewModel<Note?>(repository) {

    val note = (startIntent.getSerializableExtra(NOTE_KEY) as Note?) ?: Note()

    init {
        scope.launch {
            viewStateChannel.send(note)
        }
    }

    fun saveCurrentNote() = scope.launch {
        note.id =
            repository.saveNote(note)
    }

    fun onColorSelected(@ColorInt color: Int) {
        note.color = color

        scope.launch {
            viewStateChannel.send(note)
        }

        saveCurrentNote()
    }
}