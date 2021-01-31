package org.romeo.noteskotlin.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.romeo.noteskotlin.create_edit_note.CreateEditActivity
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository
import org.romeo.noteskotlin.model.Result
import kotlin.reflect.KClass

class MainViewModel : ViewModel(), NotesAdapter.NoteClickListener {
    companion object {
        const val TAG = "MAIN_VIEW_MODEL"
    }

    private val notesListLiveDataUi: MutableLiveData<MutableList<Note>> = MutableLiveData()
    private val startActivityLiveDataUi: MutableLiveData<KClass<CreateEditActivity>> =
        MutableLiveData() //TODO: Use generics to be able to start any activity

    init {
        Repository.getNotesListLiveData().observeForever { notes ->
            notesListLiveDataUi.value = notes
        }
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

    override fun onLongClick(note: Note): Boolean {
        return Repository.removeNote(note.hashCode()) == Result.SUCCESS
    }

    override fun onClick(note: Note) {
        Log.d(TAG, "onClick: onClick")
    }
}