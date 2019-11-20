package com.eb.kotlinwithdi.common.models


import retrofit2.http.Field


data class Post(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
)