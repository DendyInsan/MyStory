package com.dicoding.mystory.view.login
import android.util.Log
import androidx.lifecycle.*
import com.dicoding.mystory.api.ApiConfig
import com.dicoding.mystory.data.LoginResponse
import com.dicoding.mystory.data.SignInBody
import com.dicoding.mystory.model.UserModel
import com.dicoding.mystory.model.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val _failure = MutableLiveData<String>()
    private val _failureCon = MutableLiveData<Boolean>()
    val failureCon: LiveData<Boolean> = _failureCon



    fun login(Email:String, Password:String ) {

        val signInInfo = SignInBody(Email, Password)
        val client = ApiConfig.getApiService().signin(signInInfo)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()


                    if (responseBody != null && !responseBody.error!!) {
                         _failureCon.value = false
                        viewModelScope.launch {
                            pref.login()
                        }

                        val logins = UserModel(responseBody.loginResult?.token!!,responseBody.loginResult.name!!,true)
                        viewModelScope.launch {
                            pref.saveUser(logins)
                        }
                    }
                } else {
                    _failureCon.value = true
                    _failure.value = "onFailure:  ${response.message()}"

                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _failureCon.value = true
                _failure.value = "onFailure:  ${t.message}"

            }
        })
    }


}