package org.romeo.noteskotlin.model

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

interface FirebaseDataProviderTemplate {
    val currentUser: FirebaseUser?

    fun subscribeNotesListChanges(repository: Repository): ResultNote.Status
    fun saveNote(note: Note): String
    fun removeNoteById(noteId: String): ResultNote.Status
    fun getCurrentUserLiveData(): LiveData<FirebaseUser?>
}