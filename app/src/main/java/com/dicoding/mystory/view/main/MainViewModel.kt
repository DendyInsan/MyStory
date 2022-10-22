package com.dicoding.mystory.view.main


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mystory.data.fordb.StoryResponseDB
import com.dicoding.mystory.model.UserModel
import com.dicoding.mystory.model.UserPreference
import com.dicoding.mystory.repository.StoryRepository
import kotlinx.coroutines.launch


class MainViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}