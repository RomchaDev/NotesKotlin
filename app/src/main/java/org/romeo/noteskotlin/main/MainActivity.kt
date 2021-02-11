package org.romeo.noteskotlin.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import org.romeo.noteskotlin.base.BaseActivity
import org.romeo.noteskotlin.databinding.ActivityMainBinding
import org.romeo.noteskotlin.model.Note

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override var viewModel: MainViewModel = ViewModelProvider
        .NewInstanceFactory().create(MainViewModel::class.java)

    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val recyclerLiveData:
            MutableLiveData<List<Note>> = MutableLiveData()

    override fun initViews() {
        initRecycler()

        binding.viewModel = viewModel
    }

    private fun initRecycler() {
        val recycler = binding.addsRecycler
        val layoutManager = GridLayoutManager(this, 2)
        val adapter =
            NotesAdapter(
                recyclerLiveData,
                viewModel as NotesAdapter.NoteClickListener
            )

        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    override fun processData(data: List<Note>?) {
        data.let { recyclerLiveData.value = it }
    }
}