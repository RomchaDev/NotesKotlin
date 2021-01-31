package org.romeo.noteskotlin.model

import android.os.Parcelable
import java.io.Serializable

/**
 * This class may probably be used
 * in different collections. It can
 * be useful to be able to compare
 * notes. That's why it is a data class.
 * */


data class Note(
    var title: String = "",
    var content: String = ""
) : Serializable {
    override fun hashCode(): Int {
        return super.hashCode()
    }
}