package com.dicoding.mystory.view.main

import android.content.Context

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.dicoding.mystory.data.fordb.StoryResponseDB
import com.dicoding.mystory.di.Injection
import com.dicoding.mystory.repository.StoryRepository


class MainViewModelForRV  (storyRepository: StoryRepository) : ViewModel() {

    val story: LiveData<PagingData<StoryResponseDB>> =
        storyRepository.getStory().cachedIn(viewModelScope)


}

class ViewModelFactoryx(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModelForRV::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModelForRV(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}