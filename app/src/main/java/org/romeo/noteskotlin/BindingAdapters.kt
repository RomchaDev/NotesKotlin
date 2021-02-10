package org.romeo.noteskotlin

import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.google.android.material.textfield.TextInputEditText
import org.romeo.noteskotlin.create_edit_note.CreateEditViewModel

/*@BindingAdapter("title_adapter")
fun setTitle(title: EditText, vm: CreateEditViewModel?) {
    val text = title.text.toString()
    vm?.title = text
}*/

@BindingAdapter("content_adapter")
fun setContent(content: TextInputEditText, vm: CreateEditViewModel?) {
    val text = content.text.toString()
    vm?.content = text
}

