package org.romeo.noteskotlin.create_edit_note

import androidx.constraintlayout.widget.Constraints
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import org.romeo.noteskotlin.base.BaseActivity
import org.romeo.noteskotlin.databinding.ActivityNoteBinding
import org.romeo.noteskotlin.model.Note

class CreateEditActivity : BaseActivity<Note?, CreateEditViewState>() {
    override val viewModel by lazy {
        ViewModelProvider(
            this,
            CreateEditViewModelFactory(intent)
        ).get(CreateEditViewModel::class.java)
    }

    override val binding: ActivityNoteBinding by lazy {
        ActivityNoteBinding.inflate(layoutInflater)
    }

    override fun processData(t: Note?) {
    }

    override fun initViews() {
        val root = binding.root

        root.layoutParams.width = Constraints.LayoutParams.MATCH_PARENT
        root.layoutParams.height = Constraints.LayoutParams.MATCH_PARENT
            /*Constraints.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT*/


        binding.viewModel = viewModel

        binding.title.doAfterTextChanged { viewModel.saveCurrentNote() }

        binding.content.doAfterTextChanged { viewModel.saveCurrentNote() }
    }
}