package com.dicoding.mystory.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.api.ApiConfig
import com.dicoding.mystory.data.RegisterResponse
import com.dicoding.mystory.data.UserBody
import com.dicoding.mystory.model.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel (private val pref: UserPreference) : ViewModel(){
    private val _failure = MutableLiveData<String>()
    private val _failureCon = MutableLiveData<Boolean>()
    val failure: LiveData<String> = _failure
    val failureCon: LiveData<Boolean> = _failureCon



    fun register(Name:String, Email:String, Password:String ) {

        val signUpInfo = UserBody(Name, Email, Password)
        val client = ApiConfig.getApiService().registeruser(signUpInfo)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
Log.d("isSucessfull","value: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        _failureCon.value = false
                        viewModelScope.launch {
                            pref.login()
                        }


                    }
                } else {

                    _failureCon.value = true
                    _failure.value = "onFailure:  ${response.message()}"

                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _failure.value = "onFailure:  ${t.cause}"
                // Log.e("Failure", "onFailure: ${t.message}")
            }
        })
    }
}