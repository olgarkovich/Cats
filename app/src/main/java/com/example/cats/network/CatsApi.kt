package com.example.cats.network

import com.example.cats.model.Cat
import com.example.cats.model.CatDetails
import com.example.cats.model.CatImage
import retrofit2.Response
import retrofit2.http.*

private const val KEY = "f935d429-3182-45d0-b55e-55252a4095f7"
private const val LIMIT = 10

interface CatsApi {

    @GET("images/search")
    suspend fun getCats(
        @Header("x-api-key") apiKey: String = KEY,
        @Query("limit") limit: Int = LIMIT,
        @Query("page") page: Int,
        @Query("order") order: String = "desc"
    ): List<CatImage>

    @GET("images/{image_id}")
    suspend fun getCatById(
        @Header("x-api-key") apiKey: String = KEY,
        @Path("image_id") id: String
    ): CatImage

}
