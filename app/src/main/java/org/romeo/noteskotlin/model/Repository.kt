package org.romeo.noteskotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {
    private val dataProvider:
            FirebaseDataProviderTemplate = FirebaseDataProvider()

    private val notesListLiveData:
            MutableLiveData<ResultNote<MutableList<Note>>?> = MutableLiveData()

    var notes: MutableList<Note> = mutableListOf()
        set(value) {
            field = value
            notesListLiveData.value = ResultNote.Data(notes)
        }

    init {
        notesListLiveData.value = ResultNote.Data(notes)
        takeIf {
            dataProvider.subscribeNotesListChanges(this) ==
                    ResultNote.Status.SERVER_ERROR
        }?.notesListLiveData?.value = ResultNote.Error(NullPointerException())
    }

    fun getNotesListLiveData():
            LiveData<ResultNote<MutableList<Note>>?> = notesListLiveData


    fun saveNote(note: Note) =
        dataProvider.saveNote(note)


    fun removeNote(noteId: String) =
        dataProvider.removeNoteById(noteId)


    fun getCurrentUserLiveData() =
        dataProvider.getCurrentUserLiveData()
}