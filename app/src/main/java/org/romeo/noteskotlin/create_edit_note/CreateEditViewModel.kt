package org.romeo.noteskotlin.create_edit_note

import android.content.Intent
import androidx.annotation.ColorInt
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
class CreateEditViewModel(startIntent: Intent) :
    BaseViewModel<Note?, CreateEditViewState>() {

    val note = (startIntent.getSerializableExtra(NOTE_KEY) as Note?) ?: Note()

    init {
        viewStateLiveData.value =
            CreateEditViewState(note, null)
    }

    fun saveCurrentNote() {
        note.id = Repository
            .saveNote(note)
    }

    fun onColorSelected(@ColorInt color: Int) {
        note.color = color

        viewStateLiveData.value =
            CreateEditViewState(note, null)

        saveCurrentNote()
    }
}