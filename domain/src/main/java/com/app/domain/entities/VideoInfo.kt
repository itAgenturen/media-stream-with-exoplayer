package com.app.domain.entities

import java.io.Serializable

data class VideoInfo(
    val id: Int,
    val title: String,
    val image: String,
    val production: String,
    val url: String,
    val type: String,
    val isLive: Boolean
) : Serializable