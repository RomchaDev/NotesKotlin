package org.romeo.noteskotlin.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.romeo.noteskotlin.create_edit_note.CreateEditActivity
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository
import kotlin.reflect.KClass

class MainViewModel : ViewModel() {
    private val oneNoteLiveDataUi: MutableLiveData<Note> = MutableLiveData()
    private val notesListLiveDataUi: MutableLiveData<MutableList<Note>> = MutableLiveData()
    private val startActivityLiveDataUi: MutableLiveData<KClass<CreateEditActivity>> =
        MutableLiveData() //TODO: Use generics to be able to start any activity

    init {
        Repository.getOneNoteLiveData().observeForever { note ->
            oneNoteLiveDataUi.value = note
        }

        Repository.getNotesListLiveData().observeForever { notes ->
            notesListLiveDataUi.value = notes
        }
    }

    fun getInsertNoteLiveData(): LiveData<Note> {
        return oneNoteLiveDataUi
    }

    fun getNotesListLiveData(): LiveData<MutableList<Note>> {
        return notesListLiveDataUi
    }

    fun getStartActivityLiveData(): LiveData<KClass<CreateEditActivity>> {
        return startActivityLiveDataUi
    }

    fun onCreateNotePressed() {
        startActivityLiveDataUi.value = CreateEditActivity::class
    }
}