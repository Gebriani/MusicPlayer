package com.orcaella.mymusicplayer.presentation.searchmusic

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
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
class MainActivity : AppCompatActivity(), MusicCellClickListener {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var adapter: MainItemAdapter

    private lateinit var mediaPlayer: MediaPlayer

    private var pause = false

    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        getData("dua")
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainItemAdapter(arrayListOf(), this)
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

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }

        initializeSeekBar()
        butPlay.setOnClickListener {
            if (pause) {
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause = false
                butPlay.text = "Pause"
            } else {
                butPlay.text = "Play"
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

    private fun getData(searchKey: String) {
        lifecycleScope.launch {
            delay(500)
            viewModel.getMusicList(searchKey).collect {
                when (it) {
                    is State.DataState -> {
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

    override fun onStartMusicClickListener(urlString: String) {
        if (mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            pause = false
        }

        mediaPlayer.setDataSource(urlString)
        mediaPlayer.prepare()
        mediaPlayer.start()
        constraintPlayBut.visibility = View.VISIBLE
        butPlay.text = "Pause"
    }

    override fun onPauseMusicClickListener() {
        pause = true
        mediaPlayer.pause()
        butPlay.text = "Play"
    }

    override fun onBackwardMusicClickListener() {
        TODO("Not yet implemented")
    }

    override fun onForwardMusicClickListener() {
        TODO("Not yet implemented")
    }

    private fun initializeSeekBar() {
        seekbarMusic.max = mediaPlayer.seconds

        runnable = Runnable {
            seekbarMusic.progress = mediaPlayer.currentSeconds
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }

    val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }
}

