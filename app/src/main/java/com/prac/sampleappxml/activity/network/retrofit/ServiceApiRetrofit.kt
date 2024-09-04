package com.prac.sampleappxml.activity.network.retrofit

import com.prac.sampleappxml.activity.models.MemeModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ServiceApiRetrofit {

    @GET("/gimme/{count}")
    suspend fun getMemesList(
        @Path("count") count : Int
    ): Response<MemeModel>


    companion object {
        operator fun invoke(): ServiceApiRetrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(50000, TimeUnit.SECONDS)
                .readTimeout(50000, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(" https://meme-api.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ServiceApiRetrofit::class.java)
        }
    }
}