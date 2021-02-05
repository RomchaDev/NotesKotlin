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
import org.romeo.noteskotlin.DEFAULT_NOTE_ID_VALUE

private const val NOTES_COLLECTION = "NOTES_COLLECTION"
private const val USERS_COLLECTION = "USERS_COLLECTION"


/**
 * Class to work with data from firestore database
 * */
class FirebaseDataProvider : FirebaseDataProviderTemplate {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = database.collection(USERS_COLLECTION)

    override val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    companion object {
        const val TAG = "FIREBASE_DATA_PROVIDER"
    }

    /**
     * When new note is edited, this method
     * resets Repository's notes list
     * */
    override fun subscribeNotesListChanges(repository: Repository): ResultNote.Status {
        var result = ResultNote.Status.SAVE_ERROR
        try {
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

                        repository.notes = notes
                        result = ResultNote.Status.SUCCESS
                    }
                }

            return result
        } catch (e: RuntimeException) {
            return ResultNote.Status.SERVER_ERROR
        }
    }

    override fun saveNote(note: Note): String {
        return try {
            val id = if (note.id == DEFAULT_NOTE_ID_VALUE)
                getCurrentUserNotes().document().id
            else note.id

            getCurrentUserNotes()
                .document(id)
                .set(note)
                .addOnSuccessListener {
                    Log.d(TAG, "addNewNote: success: ${note.id}")
                }.addOnFailureListener {
                    Log.d(TAG, "addNewNote: failure: ${note.id}")
                    it.printStackTrace()
                }
            id
        } catch (e: RuntimeException) {
            DEFAULT_NOTE_ID_VALUE
        }

    }


    override fun removeNoteById(noteId: String): ResultNote.Status {
        var result = ResultNote.Status.REMOVE_ERROR

        try {
            getCurrentUserNotes()
                .document(noteId)
                .delete()
                .addOnSuccessListener {
                    result = ResultNote.Status.SUCCESS
                    Log.d(TAG, "removeNoteById: success: $noteId")
                }.addOnFailureListener {
                    Log.d(TAG, "removeNoteById: failure: $noteId")
                    it.printStackTrace()
                }

            return result
        } catch (e: RuntimeException) {
            return ResultNote.Status.REMOVE_ERROR
        }

    }

    override fun getCurrentUserLiveData(): LiveData<FirebaseUser?> {
        val result = MutableLiveData<FirebaseUser?>()

        currentUser?.let {
            result.value = it
            return@getCurrentUserLiveData result
        } ?: return result
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
}