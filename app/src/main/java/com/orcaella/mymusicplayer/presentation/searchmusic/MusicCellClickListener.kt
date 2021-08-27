package com.orcaella.mymusicplayer.presentation.searchmusic

interface MusicCellClickListener {
    fun onStartMusicClickListener(urlString: String)
    fun onPauseMusicClickListener()
    fun onBackwardMusicClickListener()
    fun onForwardMusicClickListener()
}