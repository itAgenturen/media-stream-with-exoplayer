package com.app.data.localsource.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocalVideo(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "production")
    val production: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "isLive")
    val isLive: Boolean
)