package com.orcaella.mymusicplayer.data

import androidx.annotation.Keep

@Keep
data class ResultEntity(
    val resultCount: Int? = 0,
    val results: List<MusicEntity>?
) {
    @Keep
    data class MusicEntity(
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
        val trackTimeMillis: Long?,
        val previewUrl: String?
    )

    fun toMusicDataDetail(): List<MusicDetail> {
        val content = results?.map {
            MusicDetail(
                wrapperType = it.wrapperType.orEmpty(),
                kind = it.kind.orEmpty(),
                artistId = it.artistId ?: 0,
                collectionId = it.collectionId ?: 0,
                trackId = it.trackId ?: 0,
                artistName = it.artistName.orEmpty(),
                collectionName = it.collectionName.orEmpty(),
                trackName = it.trackName.orEmpty(),
                artworkUrl60 = it.artworkUrl60.orEmpty(),
                artworkUrl100 = it.artworkUrl100.orEmpty(),
                trackCount = it.trackCount ?: 0,
                trackNumber = it.trackNumber ?: 0,
                trackTimeMillis = it.trackTimeMillis ?: 0,
                previewUrl = it.previewUrl.orEmpty()
            )
        } ?: listOf()
        return content
    }
}