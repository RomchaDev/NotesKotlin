package org.romeo.noteskotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

object Repository {
    private var nextNoteId = 0L
    private val dataProvider: FirebaseDataProviderTemplate = FirebaseDataProvider()
    private val notesListLiveData: MutableLiveData<MutableList<Note>> = MutableLiveData()
    private val userLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()

    var notes: MutableList<Note> = mutableListOf()
        set(value) {
            field = value
            notesListLiveData.value = notes
        }

    init {
        notesListLiveData.value = notes
        dataProvider.subscribeNotesListChanged(this)
        userLiveData.value = dataProvider.currentUser
    }

    fun getNotesListLiveData(): LiveData<MutableList<Note>> {
        return notesListLiveData
    }

    fun editNote(noteId: Long?, newNote: Note?): Result {
        if (newNote == null || noteId == null)
            return Result.SAVE_ERROR

        for (note in notes) {
            if (note.id == noteId) {
                return dataProvider.editNoteById(newNote.id, newNote)
            }
        }

        return Result.SAVE_ERROR
    }

    fun saveNote(note: Note): Result {
        note.id = nextNoteId++

        return dataProvider.saveNote(note)
    }

    fun removeNote(noteId: Long): Result {
        for (note in notes) {
            if (note.id == noteId) {
                return dataProvider.removeNoteById(note.id)
            }
        }

        return Result.REMOVE_ERROR
    }

    fun getCurrentUserLiveData() = userLiveData as LiveData<FirebaseUser?>
}