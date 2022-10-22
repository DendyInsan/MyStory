package com.dicoding.mystory.api



import com.dicoding.mystory.data.*
import com.dicoding.mystory.data.fordb.StoryResponseDB
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type:application/json")
    @POST("login")
    fun signin(
        @Body info: SignInBody
    ): Call<LoginResponse>

    @Headers("Content-Type:application/json")
    @POST("register")
    fun registeruser(
        @Body info: UserBody
    ): Call<RegisterResponse>

    @Headers("Content-Type:application/json")
    @GET("stories")
    suspend fun getallstories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
         ): List<StoryResponseDB>


    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>

}

data class FileUploadResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String
)