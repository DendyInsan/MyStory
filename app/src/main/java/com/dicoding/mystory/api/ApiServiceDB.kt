package com.dicoding.mystory.api

import com.dicoding.mystory.data.fordb.StoryResponseDB
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceDB {
    @GET("list")
    suspend fun getStory(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<StoryResponseDB>
}