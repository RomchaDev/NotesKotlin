package org.romeo.noteskotlin.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.asFlow
import org.romeo.noteskotlin.DEFAULT_NOTE_ID_VALUE
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NOTES_COLLECTION = "NOTES_COLLECTION"
private const val USERS_COLLECTION = "USERS_COLLECTION"


/**
 * Class to work with data from firestore database
 * */
class FirebaseDataProvider(database: FirebaseFirestore, private val firebaseAuth: FirebaseAuth) :
    FirebaseDataProviderTemplate {

    private val usersCollection = database.collection(USERS_COLLECTION)

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private val notesChannel: BroadcastChannel<List<Note>> =
        BroadcastChannel(Channel.CONFLATED)

    override val currentUser
        get() = firebaseAuth.currentUser

    /**
     * When new note is edited, this method
     * resets Repository's notes list
     * */
    @Throws(FirebaseFirestoreException::class)
    override suspend fun subscribeToNotesListChanges(repository: Repository) {
        scope.launch {
            getCurrentUserNotes()
                .addSnapshotListener { querySnapshot: QuerySnapshot?,
                                       firebaseFirestoreException: FirebaseFirestoreException? ->

                    firebaseFirestoreException?.let {
                        throw it
                    } ?: querySnapshot?.let { querySnapshotNN ->
                        val notes: MutableList<Note> = mutableListOf()

                        for (doc in querySnapshotNN) {
                            notes.add(doc.toObject(Note::class.java))
                        }

                        //repository.notes = notes
                        scope.launch {
                            repository.notes = notes
                        }

                        runBlocking {
                            notesChannel.send(notes)
                        }
                    }
                }
        }
    }


    override suspend fun saveNote(note: Note): String =
        suspendCoroutine { continuation ->
            try {
                val id = if (note.id == DEFAULT_NOTE_ID_VALUE)
                    getCurrentUserNotes().document().id
                else note.id

                note.id = id

                getCurrentUserNotes()
                    .document(id)
                    .set(note)
                    .addOnSuccessListener {
                        Log.d(TAG, "addNewNote: success: ${note.id}")
                        continuation.resume(id)
                    }.addOnFailureListener { exception ->
                        Log.d(TAG, "addNewNote: failure: ${note.id}")
                        exception.printStackTrace()
                        continuation.resumeWithException(exception)
                    }
            } catch (e: RuntimeException) {
                DEFAULT_NOTE_ID_VALUE
            }
        }

    override suspend fun removeNoteById(noteId: String): Boolean =
        suspendCoroutine { continuation ->
            getCurrentUserNotes()
                .document(noteId)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(true)
                    Log.d(TAG, "removeNoteById: success: $noteId")
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "removeNoteById: failure: $noteId")
                    exception.printStackTrace()
                    continuation.resumeWithException(exception)
                }
        }


    override suspend fun getCurrentUser(): FirebaseUser? =
        suspendCoroutine { continuation ->
            currentUser?.let { user ->
                continuation.resume(user)
            } ?: continuation.resume(null)
        }


    /**
     * Returns notes collection belonging
     * to current user.
     * */
    private fun getCurrentUserNotes(): CollectionReference =
        currentUser?.let {
            usersCollection.document(it.uid)
                .collection(NOTES_COLLECTION)
        } ?: throw RuntimeException("User cannot be null")

    companion object {
        const val TAG = "FIREBASE_DATA_PROVIDER"
    }
}