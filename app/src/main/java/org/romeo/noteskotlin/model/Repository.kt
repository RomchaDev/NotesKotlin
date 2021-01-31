package org.romeo.noteskotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {
    private val notes: MutableList<Note> = mutableListOf()
    private val notesListLiveData: MutableLiveData<MutableList<Note>> = MutableLiveData()

    init {
        notesListLiveData.value = notes
    }

    fun getNotesListLiveData(): LiveData<MutableList<Note>> {
        return notesListLiveData
    }

    fun replaceNote(oldHash: Int, newNote: Note): Result {
        for (i in 0..notes.size) {
            if (notes[i].hashCode() == oldHash) {
                notes[i] = newNote
                return Result.SUCCESS
            }
        }

        return Result.SAVE_ERROR
    }

    fun addNewNote(note: Note): Result {
        return try {
            notes.add(note)
            notesListLiveData.value = notes
            Result.SUCCESS
        } catch (e: Exception) {
            Result.SAVE_ERROR
        }
    }

    fun removeNote(hash: Int): Result {
        for (i in 0..notes.size) {
            if (notes[i].hashCode() == hash) {
                notes.removeAt(i)
                notesListLiveData.value = notes
                return Result.SUCCESS
            }
        }

        return Result.REMOVE_ERROR
    }
}