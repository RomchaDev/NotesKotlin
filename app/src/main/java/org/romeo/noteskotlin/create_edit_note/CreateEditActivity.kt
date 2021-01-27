package org.romeo.noteskotlin.create_edit_note

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import org.romeo.noteskotlin.databinding.NoteBinding

class CreateEditActivity : AppCompatActivity() {
    private val viewModel = CreateEditViewModel()

    private val binding: NoteBinding by lazy {
        NoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = binding.root

        root.layoutParams = ViewGroup.LayoutParams(
            Constraints.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

        setContentView(root)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onNoteAdded(binding.title.text.toString(), binding.content.text.toString())
    }
}