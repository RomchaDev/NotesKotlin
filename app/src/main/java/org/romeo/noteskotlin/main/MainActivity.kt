package org.romeo.noteskotlin.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import org.romeo.noteskotlin.ACTIVITY_TO_START
import org.romeo.noteskotlin.NOTE_KEY
import org.romeo.noteskotlin.create_edit_note.CreateEditActivity
import org.romeo.noteskotlin.databinding.ActivityMainBinding
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel = ViewModelProvider
        .NewInstanceFactory().create(MainViewModel::class.java)

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecycler()

        binding.viewModel = viewModel

        viewModel.getStartActivityLiveData().observe(this) { inIntent ->
            val classJava = inIntent.getSerializableExtra(ACTIVITY_TO_START) as Class<*>

            val startIntent = Intent(this, classJava)

            startIntent.putExtra(NOTE_KEY, inIntent.getSerializableExtra(NOTE_KEY))

            startActivity(startIntent)

        }
    }

    private fun initRecycler() {
        val recycler = binding.addsRecycler
        val layoutManager = GridLayoutManager(this, 2)
        val adapter =
            NotesAdapter(viewModel)

        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }
}