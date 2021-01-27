package org.romeo.noteskotlin.model

enum class Result(status: String) {
    SUCCESS("SUCCESS"),
    SAVE_ERROR("SAVE_ERROR"),
    NOT_CORRECT_DATA("NOT_CORRECT_DATA"),
    SERVER_ERROR("SERVER_ERROR");
}