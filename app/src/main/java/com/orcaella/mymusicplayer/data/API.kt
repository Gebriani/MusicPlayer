package com.orcaella.mymusicplayer.data

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("/search")
    suspend fun getMusicListWithModel(
        @Query("term") searchKey: String
    ): Response <ResultEntity>

    @GET("/search")
    suspend fun getMusicList(
        @Query("term") searchKey: String
    ): JsonObject
}