package com.app.exoplayerdemo.ui.player

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : ViewModel() {
    val videoProgressMap = HashMap<Int, SavedPosition>()

    fun setSavedPosition(
        id: Int,
        startAutoPlay: Boolean,
        startItemIndex: Int,
        startPosition: Long
    ) {
        val savedPosition = SavedPosition(startAutoPlay, startItemIndex, startPosition)
        videoProgressMap[id] = savedPosition
    }
}

data class SavedPosition(
    val startAutoPlay: Boolean,
    val startItemIndex: Int,
    val startPosition: Long
)