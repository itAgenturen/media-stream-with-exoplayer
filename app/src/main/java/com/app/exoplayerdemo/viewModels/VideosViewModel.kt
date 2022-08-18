package com.app.exoplayerdemo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.entities.VideoInfo
import com.app.domain.usecases.GetVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(private val useCase: GetVideoUseCase) : ViewModel() {

    private var _videos = MutableLiveData<List<VideoInfo>>()
    var videos: LiveData<List<VideoInfo>> = _videos

    init {
        getVideos()
    }

    private fun getVideos() {
        viewModelScope.launch {
            _videos.value = useCase.invoke()
        }
    }
}