package org.romeo.noteskotlin

import android.content.Context
import android.graphics.Color

const val NOTE_KEY = "NOTE_KEY"
const val ACTIVITY_TO_START = "ACTIVITY_TO_START"
const val DEFAULT_NOTE_ID_VALUE = "-1"

fun Context.dip(value: Int) =
    (value * resources.displayMetrics.density).toInt()


fun colors(): List<Int> {
    return mutableListOf(
        Color.WHITE,
        Color.BLACK,
        Color.YELLOW,
        Color.RED,
        Color.BLUE,
        Color.GRAY,
        Color.GREEN
    )
}