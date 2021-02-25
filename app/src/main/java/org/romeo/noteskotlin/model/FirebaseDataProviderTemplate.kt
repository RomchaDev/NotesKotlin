package org.romeo.noteskotlin.model

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

interface FirebaseDataProviderTemplate {
    val currentUser: FirebaseUser?

    suspend fun subscribeToNotesListChanges(repository: Repository)
    suspend fun saveNote(note: Note): String
    suspend fun removeNoteById(noteId: String): Boolean
    suspend fun getCurrentUser(): FirebaseUser?
}