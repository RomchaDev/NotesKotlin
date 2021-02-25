package org.romeo.noteskotlin.main

import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import org.romeo.noteskotlin.NOTE_KEY
import org.romeo.noteskotlin.R
import org.romeo.noteskotlin.base.BaseActivity
import org.romeo.noteskotlin.create_edit_note.CreateEditActivity
import org.romeo.noteskotlin.databinding.ActivityMainBinding
import org.romeo.noteskotlin.model.Note

class MainActivity : BaseActivity<List<Note>?>(), NotesAdapter.NoteClickListener {
    override val viewModel: MainViewModel by viewModel()

    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val recyclerLiveData:
            MutableLiveData<List<Note>> = MutableLiveData()

    override fun initViews() {
        initRecycler()

        binding.activity = this
    }

    private fun initRecycler() {
        val recycler = binding.addsRecycler
        val layoutManager = GridLayoutManager(this, 2)
        val adapter =
            NotesAdapter(
                recyclerLiveData,
                this
            )

        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    override fun processData(data: List<Note>?) {
        data.let { recyclerLiveData.value = it }
    }

    /**
     * It is called when user long clicked to
     * the notes RecyclerView's item
     * @return true if note was deleted
     * @return false if note was not deleted
     * */
    override fun onLongClick(note: Note, view: View) {
        val menu = PopupMenu(this, view)
        menu.inflate(R.menu.menu_note_long_click)

        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit_note_menu_item -> {
                    startEditActivity(note)
                    true
                }

                R.id.delete_note_menu_item -> {
                    viewModel.removeNote(note)
                    true
                }

                else -> false
            }
        }

        menu.show()
    }


    /**
     * It is called when user clicked to
     * the notes RecyclerView's item
     * */
    override fun onClick(note: Note) {
        startEditActivity(note)
    }

    private fun startEditActivity(note: Note) {
        val intent = Intent(
            this,
            CreateEditActivity::class.java
        )

        intent.putExtra(NOTE_KEY, note)

        startActivity(intent)
    }

    /**
     * It is called from xml when user pressed
     * on the create FAB
     * */
    fun onCreateNotePressed() {
        startActivity(
            Intent(this, CreateEditActivity::class.java)
        )
    }
}