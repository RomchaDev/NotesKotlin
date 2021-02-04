package org.romeo.noteskotlin.model

import com.google.firebase.auth.FirebaseUser

interface FirebaseDataProviderTemplate {
    val currentUser: FirebaseUser?

    fun subscribeNotesListChanged(repository: Repository): Result
    fun saveNote(note: Note): Result
    fun removeNoteById(noteId: Long): Result
    fun editNoteById(noteId: Long, newNote: Note): Result
}