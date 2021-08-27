package com.orcaella.mymusicplayer.domain

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.orcaella.mymusicplayer.data.API
import com.orcaella.mymusicplayer.data.MusicDetail
import com.orcaella.mymusicplayer.data.ResultEntity
import retrofit2.Response
import javax.inject.Inject

class GetMusicListUseCase @Inject constructor(
    private val apIs: API
){

    suspend operator fun invoke(searchkey: String): List<MusicDetail>{
        val response = apIs.getMusicList(searchkey)
        var result = Gson().fromJson(response.toString(), ResultEntity::class.java)
        return result.toMusicDataDetail()
    }

}