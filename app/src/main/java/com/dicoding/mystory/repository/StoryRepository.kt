package com.dicoding.mystory.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.dicoding.mystory.api.ApiService
import com.dicoding.mystory.api.ApiServiceDB
import com.dicoding.mystory.data.StoryRemoteMediator
import com.dicoding.mystory.data.fordb.StoryResponseDB
import com.dicoding.mystory.database.StoryDatabase

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    fun getStory(): LiveData<PagingData<StoryResponseDB>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
//                QuotePagingSource(apiService)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}

