package com.app.data.localsource.mappers

import com.app.data.base.Mapper
import com.app.data.localsource.entities.LocalVideo
import com.app.domain.entities.VideoInfo

class LocalVideoMapper : Mapper<LocalVideo, VideoInfo> {
    override fun map(input: LocalVideo): VideoInfo {
        return VideoInfo(
            id = input.id,
            title = input.name,
            image = input.image,
            production = input.production,
            url = input.url,
            type = input.type,
            isLive = input.isLive
        )
    }
}