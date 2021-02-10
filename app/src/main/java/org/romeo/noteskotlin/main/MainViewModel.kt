package org.romeo.noteskotlin.main

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.romeo.noteskotlin.ACTIVITY_TO_START
import org.romeo.noteskotlin.NOTE_KEY
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
    private val startActivityLiveDataUi: MutableLiveData<Intent> =
        MutableLiveData()

    init {
        Repository.getNotesListLiveData().observeForever { notes ->
            notesListLiveDataUi.value = notes
        }
    }

    fun getNotesListLiveData(): LiveData<MutableList<Note>> {
        return notesListLiveDataUi
    }

    fun getStartActivityLiveData(): LiveData<Intent> {
        return startActivityLiveDataUi
    }

    /**
     * It is called from xml when user pressed
     * on the create FAB
     * */
    fun onCreateNotePressed() {
        val intent = Intent()
        intent.putExtra(ACTIVITY_TO_START, CreateEditActivity::class.java)
        startActivityLiveDataUi.value = intent
    }

    /**
     * It is called when user long clicked to
     * the notes RecyclerView's item
     * */
    override fun onLongClick(note: Note): Boolean {
        return Repository.removeNote(note.id) == Result.SUCCESS
    }

    /**
     * It is called when user clicked to
     * the notes RecyclerView's item
     * */
    override fun onClick(note: Note) {
        val intent = Intent()
        intent.putExtra(ACTIVITY_TO_START, CreateEditActivity::class.java)
        intent.putExtra(NOTE_KEY, note)
        startActivityLiveDataUi.value = intent
    }
}