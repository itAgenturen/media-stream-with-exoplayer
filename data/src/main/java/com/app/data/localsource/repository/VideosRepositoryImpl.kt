package com.app.data.localsource.repository

import android.content.res.AssetManager
import com.app.data.localsource.entities.LocalVideo
import com.app.data.localsource.mappers.LocalVideoMapper
import com.app.domain.entities.VideoInfo
import com.app.domain.repositories.IVideosRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val localVideoMapper: LocalVideoMapper,
    private val moshi: Moshi
) : IVideosRepository {

    override suspend fun getVideos(): List<VideoInfo> {
        val jsonString = assetManager.open("stream_videos.json").bufferedReader().use {
            it.readText()
        }
        val listMyData = Types.newParameterizedType(List::class.java, LocalVideo::class.java)
        val jsonAdapter = moshi.adapter<List<LocalVideo>>(listMyData)
        val listLocalVideo = jsonAdapter.fromJson(jsonString)
        return listLocalVideo?.map { localVideoMapper.map(it) }.orEmpty()
    }
}