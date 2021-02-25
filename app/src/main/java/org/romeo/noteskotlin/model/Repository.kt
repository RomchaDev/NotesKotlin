package org.romeo.noteskotlin.model

import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class Repository(private val dataProvider: FirebaseDataProvider) {

    private val scope = CoroutineScope(Dispatchers.Default)

    val notesChannel: BroadcastChannel<List<Note>> =
        BroadcastChannel(Channel.CONFLATED)

    var notes: MutableList<Note> = mutableListOf()
        set(value) {
            field = value
            scope.launch { notesChannel.send(value) }
        }

    init {
        scope.launch {
            try {
                dataProvider.subscribeToNotesListChanges(this@Repository)
            } catch (e: FirebaseFirestoreException) {
                dataProvider.subscribeToNotesListChanges(this@Repository)
            }
        }
    }


    suspend fun saveNote(note: Note) =
        dataProvider.saveNote(note)


    suspend fun removeNote(noteId: String) =
        dataProvider.removeNoteById(noteId)


    suspend fun getCurrentUser() =
        dataProvider.getCurrentUser()

    //fun getNotesChannel() = dataProvider.notesChannel
}