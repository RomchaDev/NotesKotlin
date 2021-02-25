package org.romeo.noteskotlin.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import org.romeo.noteskotlin.databinding.NoteItemBinding
import org.romeo.noteskotlin.model.Note

class NotesAdapter(
    private val notesLiveData: LiveData<List<Note>>,
    private val noteClickListener: NoteClickListener
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var notes = mutableListOf<Note>()
    private val scope = CoroutineScope(Dispatchers.Default + Job())

    init {
        notesLiveData.observeForever { list ->
            notes = list as MutableList<Note>
            notifyDataSetChanged()
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

        val binding = NoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteViewHolder(binding, noteClickListener)
    }

    inner class NoteViewHolder(
        private val binding: NoteItemBinding,
        private val listener: NoteClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var note: Note? = null
        private val title = binding.title
        private val content = binding.content

        init {
            title.setOnClickListener { listener.onClick(notes[adapterPosition]) }
            title.setOnLongClickListener {
                listener.onLongClick(notes[adapterPosition], binding.root)
                true
            }

            content.setOnClickListener { listener.onClick(notes[adapterPosition]) }
            content.setOnLongClickListener {
                listener.onLongClick(notes[adapterPosition], binding.root)
                true
            }
        }

        fun bind(note: Note) {
            this.note = note

            title.text.replace(0, title.length(), note.title)
            content.text?.replace(0, content.length(), note.content)

            binding.root.setCardBackgroundColor(note.color)
            binding.mainLinear.setBackgroundColor(note.color)
        }
    }

    /**
     * ViewModel implements it to
     * react to user clicks
     * */
    interface NoteClickListener {
        fun onLongClick(note: Note, view: View)
        fun onClick(note: Note)
    }
}