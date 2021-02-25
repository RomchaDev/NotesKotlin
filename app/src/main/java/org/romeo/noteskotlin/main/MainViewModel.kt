package org.romeo.noteskotlin.main

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.romeo.noteskotlin.base.BaseViewModel
import org.romeo.noteskotlin.model.Note
import org.romeo.noteskotlin.model.Repository

class MainViewModel(repository: Repository) : BaseViewModel<List<Note>?>(repository) {

    companion object {
        const val TAG = "MAIN_VIEW_MODEL"
    }

    init {
        scope.launch {
            repository.notesChannel.consumeEach {
                viewStateChannel.send(it)
            }
        }
    }

    fun removeNote(note: Note) {
        scope.launch {
            repository.removeNote(note.id)
        }
    }
}