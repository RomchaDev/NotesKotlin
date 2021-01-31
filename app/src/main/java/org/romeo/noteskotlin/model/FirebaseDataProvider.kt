package org.romeo.noteskotlin.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import org.romeo.noteskotlin.CONTENT_KEY
import org.romeo.noteskotlin.TITLE_KEY

const val NOTES_COLLECTION = "NOTES_COLLECTION"


/**
 * Class to work with data from firestore database
 * */
class FirebaseDataProvider : FirebaseDataProviderTemplate {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference = database.collection(NOTES_COLLECTION)

    companion object {
        const val TAG = "FIREBASE_DATA_PROVIDER"
    }

    /**
     * When new note is edited, this method
     * resets Repository's notes list
     * */
    override fun subscribeNotesListChanged(repository: Repository): Result {
        var result = Result.SAVE_ERROR

        collectionReference
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
    }

    override fun saveNote(note: Note): Result {
        var result = Result.SAVE_ERROR

        collectionReference
            .document(note.id.toString())
            .set(note)
            .addOnSuccessListener { doc ->
                Log.d(TAG, "addNewNote: success: ${note.id}")
                result = Result.SUCCESS
            }.addOnFailureListener {
                Log.d(TAG, "addNewNote: failure: ${note.id}")
                it.printStackTrace()
            }
        return result
    }


    override fun removeNoteById(noteId: Long): Result {
        var result = Result.REMOVE_ERROR

        collectionReference
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
    }

    override fun editNoteById(noteId: Long, newNote: Note): Result {
        val updateMap = HashMap<String, String>()
        updateMap[TITLE_KEY] = newNote.title
        updateMap[CONTENT_KEY] = newNote.content

        collectionReference
            .document(noteId.toString())
            .update(updateMap as Map<String, Any>)

        return Result.SUCCESS
    }
}