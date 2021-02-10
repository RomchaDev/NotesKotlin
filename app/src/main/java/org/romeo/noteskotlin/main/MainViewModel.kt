package org.romeo.noteskotlin.main

import android.content.Intent
import org.romeo.noteskotlin.ACTIVITY_TO_START
import org.romeo.noteskotlin.base.BaseViewModel
import org.romeo.noteskotlin.NOTE_KEY
import org.romeo.noteskotlin.create_edit_note.CreateEditActivity
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository
import org.romeo.noteskotlin.model.ResultNote

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>(),
    NotesAdapter.NoteClickListener {

    companion object {
        const val TAG = "MAIN_VIEW_MODEL"
    }

    init {
        Repository.getNotesListLiveData().observeForever { resultNotes ->
            if (resultNotes is ResultNote.Data)
                viewStateLiveData.value =
                    MainViewState(resultNotes.data, null)
            else
                viewStateLiveData.value =
                    MainViewState(null, (resultNotes as ResultNote.Error).error)
        }
    }

    /**
     * It is called from xml when user pressed
     * on the create FAB
     * */
    fun onCreateNotePressed() {
        val intent = Intent()
        intent.putExtra(ACTIVITY_TO_START, CreateEditActivity::class.java)
        startActivityLiveData.value = intent
    }

    /**
     * It is called when user long clicked to
     * the notes RecyclerView's item
     * */
    override fun onLongClick(note: Note): Boolean {
        return Repository.removeNote(note.id) == ResultNote.Status.SUCCESS
    }

    /**
     * It is called when user clicked to
     * the notes RecyclerView's item
     * */
    override fun onClick(note: Note) {
        val intent = Intent()
        intent.putExtra(ACTIVITY_TO_START, CreateEditActivity::class.java)
        intent.putExtra(NOTE_KEY, note)
        startActivityLiveData.value = intent
    }
}