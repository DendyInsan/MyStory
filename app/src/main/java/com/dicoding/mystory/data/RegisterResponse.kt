package com.dicoding.mystory.data

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)


data class UserBody( val name: String,
                     val email: String,
                     val password: String)
