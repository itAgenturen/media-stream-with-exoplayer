package com.app.domain.repositories

import com.app.domain.entities.VideoInfo

interface IVideosRepository {

    suspend fun getVideos(): List<VideoInfo>
}