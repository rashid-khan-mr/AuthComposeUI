package com.appointment.composeauth.data.remote

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("mobile")
    var mobile: String? = "",
    @SerializedName("user_name")
    var user_name: String? = "",
    @SerializedName("password")
    var password: String? = ""
)
