package org.romeo.noteskotlin.main

import org.romeo.noteskotlin.base.BaseViewState
import org.romeo.noteskotlin.model.Note

class MainViewState(
    data: List<Note>?,
    error: Throwable?
) : BaseViewState<List<Note>?>(data, error)