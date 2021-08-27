package com.orcaella.mymusicplayer.data

data class MusicDetail(
    val wrapperType: String?,
    val kind: String?,
    val artistId: Long?,
    val collectionId: Long?,
    val trackId: Long?,
    val artistName: String?,
    val collectionName: String?,
    val trackName: String?,
    val artworkUrl60: String?,
    val artworkUrl100: String?,
    val trackCount: Long?,
    val trackNumber: Long?,
    val trackTimeMillis: Long?
)