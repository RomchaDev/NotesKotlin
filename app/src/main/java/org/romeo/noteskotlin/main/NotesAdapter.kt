package org.romeo.noteskotlin.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.romeo.noteskotlin.databinding.NoteBinding
import org.romeo.noteskotlin.model.Note

class NotesAdapter(
    oneNoteLiveData: LiveData<Note>,
    notesListLiveData: LiveData<MutableList<Note>>
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var notes = mutableListOf<Note>()

    init {
        oneNoteLiveData.observeForever { note ->
            notes.add(note)
            notifyItemInserted(notes.size)
        }

        notesListLiveData.observeForever {
            notes = it
            notifyDataSetChanged()
        }
    }

    inner class NoteViewHolder(binding: NoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val title = binding.title
        private val content = binding.content

        fun bind(note: Note) {
            title.text.replace(0, title.length(), note.title)
            content.text?.replace(0, content.length(), note.content)
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {

        val binding = NoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteViewHolder(binding)
    }
}