package org.romeo.noteskotlin.model

interface FirebaseDataProviderTemplate {
    fun subscribeNotesListChanged(repository: Repository): Result
    fun saveNote(note: Note): Result
    fun removeNoteById(noteId: Long): Result
    fun editNoteById(noteId: Long, newNote: Note): Result
}