package com.dicoding.mystory.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.dicoding.mystory.data.fordb.StoryResponseDB

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(quote: List<StoryResponseDB>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, StoryResponseDB>

    @Query("DELETE FROM story")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)
}