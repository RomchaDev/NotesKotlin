package org.romeo.noteskotlin.create_edit_note

import org.romeo.noteskotlin.base.BaseViewState
import org.romeo.noteskotlin.model.Note

class CreateEditViewState(
    note: Note? = null,
    error: Throwable? = null
) : BaseViewState<Note?>(note, error)