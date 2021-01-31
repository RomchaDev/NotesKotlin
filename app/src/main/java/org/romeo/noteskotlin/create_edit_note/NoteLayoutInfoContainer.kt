package org.romeo.noteskotlin.create_edit_note

class NoteLayoutInfoContainer(var viewModel: CreateEditViewModel? = null) {
    constructor(viewModel: CreateEditViewModel? = null, title:String, content:String) : this() {
        this.title = title
        this.content = content
        this.viewModel = viewModel
    }

    var title: String = ""
        set(value) {
            field = value
            viewModel?.title = value
        }

    var content: String = ""
        set(value) {
            field = value
            viewModel?.content = value
        }
}