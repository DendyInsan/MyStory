package com.dicoding.mystory.widget

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.mystory.api.ApiConfig
import com.dicoding.mystory.data.ListStory
import com.dicoding.mystory.data.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WidgetViewModel: ViewModel()  {
    val listStories = MutableLiveData<List<ListStory>>()
    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String> = _failure

    fun getAllStory(token:String) {
//
//        val client = ApiConfig.getApiServiceBearer(token).getallstories()
//        client.enqueue(object : Callback<StoryResponse> {
//            override fun onResponse(
//                call: Call<StoryResponse>,
//                response: Response<StoryResponse>
//            ) {
//
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null && !responseBody.error) {
//                        listStories.postValue(responseBody.listStory)
//                    }
//                } else {
//                    _failure.value = "onFailure:  ${response.message()}"
//
//                }
//            }
//            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                _failure.value = "onFailure:  ${t.message}"
//                Log.e("Failure", "onFailure: ${t.message}")
//            }
//        })
    }

    fun getStory(): LiveData<List<ListStory>> {
        return listStories
    }
}