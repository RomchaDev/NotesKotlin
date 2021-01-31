package org.romeo.noteskotlin.create_edit_note

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.ViewModelProvider
import org.romeo.noteskotlin.databinding.ActivityNoteBinding

class CreateEditActivity : AppCompatActivity() {
    private val viewModel = ViewModelProvider
        .NewInstanceFactory().create(CreateEditViewModel::class.java)

    private val binding: ActivityNoteBinding by lazy {
        ActivityNoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = binding.root

        root.layoutParams = ViewGroup.LayoutParams(
            Constraints.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

        lifecycle.addObserver(viewModel)

        binding.viewModel = viewModel

        setContentView(root)
    }

/*    override fun onPause() {
        super.onPause()
        viewModel.onNoteAdded(binding.title.text.toString(), binding.content.text.toString())
    }*/
}