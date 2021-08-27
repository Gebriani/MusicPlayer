package com.orcaella.mymusicplayer.presentation.searchmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.orcaella.mymusicplayer.R
import com.orcaella.mymusicplayer.data.MusicDetail
import com.orcaella.mymusicplayer.presentation.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var adapter: MainItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        getData("dua")
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainItemAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        buttonSearch.setOnClickListener {
            getData(searchEditTxt.text.toString())
        }
    }

    private fun getData(searchKey: String) {
        lifecycleScope.launch {
            delay(500)
            viewModel.getMusicList(searchKey).collect {
                when (it) {
                    is State.DataState -> {
                        Log.d("e", "success " + it.data)
                        it.data.let { musics -> renderList(it.data) }
                    }

                    is State.ErrorState -> {
                        Log.d("e", "error")
                    }
                }
            }

        }
    }

    private fun renderList(musics: List<MusicDetail>) {
        adapter.addData(musics)
        adapter.notifyDataSetChanged()
    }
}
