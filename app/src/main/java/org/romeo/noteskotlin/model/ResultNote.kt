package org.romeo.noteskotlin.model

sealed class ResultNote<T> {
    data class Data<T>(val data: T) : ResultNote<T>()

    data class Error<T>(val error: Throwable) : ResultNote<T>()

    enum class Status(val status: String) {
        SUCCESS("SUCCESS"),
        SAVE_ERROR("SAVE_ERROR"),
        REMOVE_ERROR("REMOVE_ERROR"),
        NOT_CORRECT_DATA("NOT_CORRECT_DATA"),
        SERVER_ERROR("SERVER_ERROR");
    }
}
