package com.example.picassoapp.service

import com.example.picassoapp.models.Photo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    fun getPhotos(): Call<List<Photo>>
}
