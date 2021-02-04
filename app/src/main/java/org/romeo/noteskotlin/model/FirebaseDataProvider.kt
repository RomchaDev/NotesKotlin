package org.romeo.noteskotlin.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import org.romeo.noteskotlin.CONTENT_KEY
import org.romeo.noteskotlin.TITLE_KEY

private const val NOTES_COLLECTION = "NOTES_COLLECTION"
private const val USERS_COLLECTION = "USERS_COLLECTION"


/**
 * Class to work with data from firestore database
 * */
class FirebaseDataProvider : FirebaseDataProviderTemplate {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = database.collection(USERS_COLLECTION)
    private val notesCollection: CollectionReference by lazy { getCurrentUserNotes() }

    override val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    companion object {
        const val TAG = "FIREBASE_DATA_PROVIDER"
    }

    /**
     * When new note is edited, this method
     * resets Repository's notes list
     * */
    override fun subscribeNotesListChanged(repository: Repository): Result {
        var result = Result.SAVE_ERROR
        try {
            notesCollection
                .addSnapshotListener { querySnapshot: QuerySnapshot?,
                                       firebaseFirestoreException: FirebaseFirestoreException? ->

                    if (firebaseFirestoreException == null && querySnapshot != null) {
                        val notes: MutableList<Note> = mutableListOf()

                        for (doc in querySnapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }

                        repository.notes = notes
                        result = Result.SUCCESS
                    }
                }

            return result
        } catch (e: RuntimeException) {
            return Result.SERVER_ERROR
        }
    }

    override fun saveNote(note: Note): Result {
        var result = Result.SAVE_ERROR

        return try {
            notesCollection
                .document(note.id.toString())
                .set(note)
                .addOnSuccessListener {
                    Log.d(TAG, "addNewNote: success: ${note.id}")
                    result = Result.SUCCESS
                }.addOnFailureListener {
                    Log.d(TAG, "addNewNote: failure: ${note.id}")
                    it.printStackTrace()
                }
            result
        } catch (e: RuntimeException) {
            Result.SAVE_ERROR
        }

    }


    override fun removeNoteById(noteId: Long): Result {
        var result = Result.REMOVE_ERROR

        try {
            notesCollection
                .document(noteId.toString())
                .delete()
                .addOnSuccessListener {
                    result = Result.SUCCESS
                    Log.d(TAG, "removeNoteById: success: $noteId")
                }.addOnFailureListener {
                    Log.d(TAG, "removeNoteById: failure: $noteId")
                    it.printStackTrace()
                }

            return result
        } catch (e: RuntimeException) {
            return Result.REMOVE_ERROR
        }

    }

    override fun editNoteById(noteId: Long, newNote: Note): Result {
        val updateMap = HashMap<String, String>()

        return try {
            updateMap[TITLE_KEY] = newNote.title
            updateMap[CONTENT_KEY] = newNote.content

            notesCollection
                .document(noteId.toString())
                .update(updateMap as Map<String, Any>)

            Result.SUCCESS
        } catch(e: RuntimeException) {
            Result.SERVER_ERROR
        }

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