package com.defneersungur.a487project

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductApi {
    private var retrofit: Retrofit? = null

    fun getProducts(): Retrofit {
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl("https://www.jsonkeeper.com/b/12SM/")
                .addConverterFactory(GsonConverterFactory.create()) //retrofit will understand as a converter GSON converter will be used
                .build()

        return retrofit as Retrofit
    }
}