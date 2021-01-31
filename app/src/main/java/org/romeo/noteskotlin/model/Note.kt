package org.romeo.noteskotlin.model

import org.romeo.noteskotlin.DEFAULT_NOTE_ID_VALUE
import java.io.Serializable

data class Note(
    var id: Long = DEFAULT_NOTE_ID_VALUE,
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
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }
}