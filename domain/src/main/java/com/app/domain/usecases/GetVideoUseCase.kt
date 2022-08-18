package com.app.domain.usecases

import com.app.domain.entities.VideoInfo
import com.app.domain.repositories.IVideosRepository
import javax.inject.Inject


class GetVideoUseCase @Inject constructor(private val repository: IVideosRepository) {

    suspend operator fun invoke(): List<VideoInfo> {
        return repository.getVideos()
    }
}