package org.romeo.noteskotlin.model

import android.os.Handler
import android.os.HandlerThread
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {
    private val notes: MutableList<Note> = mutableListOf()
    private val oneNoteLiveData: MutableLiveData<Note> = MutableLiveData()
    private val notesListLiveData: MutableLiveData<MutableList<Note>> = MutableLiveData()
    private val notesHandler: Handler by lazy {
        val ht = HandlerThread("NOTES_HANDLER_THREAD")
        ht.start()
        Handler(ht.looper)
    }

    init {
 /*       for (i in 1..10) {
            notes.add(
                Note(
                    title = "Hello$i",
                    content = "World$i !!!"
                )
            )
        }*/

        notesListLiveData.value = notes
    }

    fun insertAdd(note: Note): Result {
        notes.add(note)
        oneNoteLiveData.value = note
        return Result.SUCCESS
    }

    fun getOneNoteLiveData(): LiveData<Note> {
        return oneNoteLiveData
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
            oneNoteLiveData.value = note
            Result.SUCCESS
        } catch (e: Exception) {
            Result.SAVE_ERROR
        }
    }
}