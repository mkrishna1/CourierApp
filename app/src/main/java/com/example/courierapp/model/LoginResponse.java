package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("userId")
    String userId;

    public String getUserId() {
        return userId;
    }
}
