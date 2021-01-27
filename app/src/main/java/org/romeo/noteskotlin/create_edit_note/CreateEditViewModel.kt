package org.romeo.noteskotlin.create_edit_note

import androidx.lifecycle.ViewModel
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository

class CreateEditViewModel : ViewModel() {
    private var isAlreadyCreated = false

    fun onNoteAdded(title: String, content: String) {
        if (!isAlreadyCreated) {
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