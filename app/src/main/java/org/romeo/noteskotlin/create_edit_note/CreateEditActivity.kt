package org.romeo.noteskotlin.create_edit_note

import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import org.romeo.noteskotlin.R
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

    override fun processData(note: Note?) {
        note?.apply {
            binding.appBar.setBackgroundColor(color)
            binding.toolBar.setBackgroundColor(color)
        }
    }

    override fun initViews() {
        binding.viewModel = viewModel

        binding.title.doAfterTextChanged { viewModel.saveCurrentNote() }

        binding.content.doAfterTextChanged { viewModel.saveCurrentNote() }

        binding.colorPicker.onColorClickListener = { color ->
            viewModel.onColorSelected(color)
        }

        setSupportActionBar(binding.toolBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.palette -> {
                if (binding.colorPicker.isActive)
                    binding.colorPicker.close()
                else
                    binding.colorPicker.open()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * If color picker is opened, activity
     * will not be finished after onBackPressed(),
     * color picker will be closed instead.
     * */
    override fun onBackPressed() {
        if (binding.colorPicker.isActive) {
            binding.colorPicker.close()
        } else
            super.onBackPressed()
    }
}