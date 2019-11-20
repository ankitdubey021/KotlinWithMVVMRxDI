package com.eb.kotlinwithdi.common

import com.eb.kotlinwithdi.common.models.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

}