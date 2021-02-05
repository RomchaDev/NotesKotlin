package org.romeo.noteskotlin.model

import org.romeo.noteskotlin.DEFAULT_NOTE_ID_VALUE
import java.io.Serializable

data class Note(
    var id: String = DEFAULT_NOTE_ID_VALUE,
    var title: String = "",
    var content: String = ""
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}