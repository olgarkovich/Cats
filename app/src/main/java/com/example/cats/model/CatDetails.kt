package com.example.cats.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatDetails (

    @Json(name = "breeds")
    val catInfo: List<CatInfo>,

    @Json(name = "id")
    val id: String,

    @Json(name = "url")
    val url: String,
)
