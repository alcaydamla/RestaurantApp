package com.defneersungur.a487project

import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("https://www.jsonkeeper.com/b/12SM/")
    fun getProduct(): Call<List<ProductEntity>>
}