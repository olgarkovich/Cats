package com.example.cats.network

import com.example.cats.model.Cat
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val KEY = "f935d429-3182-45d0-b55e-55252a4095f7"
private const val LIMIT = 20

interface CatsApi {

    @GET("images/search")
    suspend fun getCats(
        @Header("x-api-key") apiKey: String = KEY,
        @Query("limit") limit: Int = LIMIT
    ): List<Cat>

    @GET("images/{image_id}")
    suspend fun getCatById(
        @Header("x-api-key") apiKey: String = KEY,
        @Field("image_id") id: String
    ): Cat
}
