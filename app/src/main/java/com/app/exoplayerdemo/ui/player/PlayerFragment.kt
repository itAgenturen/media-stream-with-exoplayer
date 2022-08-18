package com.app.exoplayerdemo.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.domain.entities.VideoInfo
import com.app.exoplayerdemo.R
import com.app.exoplayerdemo.R.*
import com.app.exoplayerdemo.databinding.FragmentPlayerBinding
import com.app.exoplayerdemo.utils.EXTRAS_VIDEOS
import com.app.exoplayerdemo.utils.TYPE_DASH_MP4
import com.app.exoplayerdemo.utils.TYPE_HLS
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.max

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val playerViewModel: PlayerViewModel by viewModels()

    private var videoInfo: VideoInfo? = null
    var player: ExoPlayer? = null
    private var startAutoPlay = false
    private var startItemIndex = 0
    private var startPosition: Long = 0
    private var goLive: TextView? = null
    private var exoPosition: TextView? = null
    private var exoDuration: TextView? = null
    private var isPlayWhenReady: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val playerControllerView: PlayerControlView =
            binding.videoPlayerView.findViewById(com.google.android.exoplayer2.ui.R.id.exo_controller)
        goLive = playerControllerView.findViewById(R.id.exo_go_live)
        exoPosition = playerControllerView.findViewById(R.id.exo_position)
        exoDuration = playerControllerView.findViewById(R.id.exo_duration)

        if (videoInfo == null && playerViewModel.videoProgressMap.isEmpty()) {
            isPlayWhenReady = true
        }
        updateStartPosition()
        videoInfo = arguments?.getSerializable(EXTRAS_VIDEOS) as VideoInfo?
        initializeSavedVideoPosition()
        releasePlayer()
        initializePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeSavedVideoPosition() {
        videoInfo?.let { video ->
            val savedPosition = playerViewModel.videoProgressMap[video.id]
            if (savedPosition != null) {
                this.startAutoPlay = savedPosition.startAutoPlay
                this.startItemIndex = savedPosition.startItemIndex
                this.startPosition = savedPosition.startPosition
            } else {
                clearStartPosition()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                videoInfo?.let { video ->
                    binding.videoPlayerView.player = exoPlayer
                    when (video.type) {
                        TYPE_DASH_MP4 -> {
                            exoPlayer.setMediaSource(getDashMediaSource(video))
                        }
                        TYPE_HLS -> {
                            exoPlayer.setMediaSource(getHlsMediaSource(video))
                        }
                        else -> {
                            throw IllegalStateException()
                        }
                    }
                    val haveStartPosition = startItemIndex != C.INDEX_UNSET
                    if (haveStartPosition) {
                        exoPlayer.seekTo(startItemIndex, startPosition)
                    }
                    exoPlayer.prepare()
                    if (isPlayWhenReady) {
                        exoPlayer.playWhenReady
                        isPlayWhenReady = false
                    } else {
                        exoPlayer.play()
                    }
                    exoPlayer.addListener(PlayerEventListener())
                }
            }
    }

    private fun getHlsMediaSource(video: VideoInfo): HlsMediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaItem = getMediaItem(video)
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }

    private fun getDashMediaSource(video: VideoInfo): DashMediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaItem = getMediaItem(video)
        return DashMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
    }

    private fun getMediaItem(video: VideoInfo): MediaItem {
        val mediaItem = MediaItem.Builder()
        mediaItem.setUri(video.url)
        return mediaItem.build()
    }

    private fun releasePlayer() {
        player?.run {
            release()
        }
        player = null
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            player?.let { updateStartPosition() }
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            player?.let { updateStartPosition() }
            releasePlayer()
        }
    }

    private fun updateStartPosition() {
        player?.run {
            startAutoPlay = playWhenReady
            startItemIndex = currentMediaItemIndex
            startPosition = max(0, contentPosition)
            videoInfo?.let {
                playerViewModel.setSavedPosition(
                    it.id,
                    startAutoPlay,
                    startItemIndex,
                    startPosition
                )
            }
        }
    }

    private fun clearStartPosition() {
        startAutoPlay = true
        startItemIndex = C.INDEX_UNSET
        startPosition = C.TIME_UNSET
    }

    inner class PlayerEventListener : Player.Listener {

        override fun onPlayerError(error: PlaybackException) {
            Timber.e("TYPE_SOURCE: " + error.errorCodeName)
            if (error.errorCode == PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW) {
                player?.run {
                    seekToDefaultPosition()
                    prepare()
                }
            }
        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            super.onTimelineChanged(timeline, reason)
            if (player?.isCurrentMediaItemLive == true) {
                player?.seekToDefaultPosition()
                goLive?.visibility = View.VISIBLE
                exoPosition?.visibility = View.GONE
                exoDuration?.visibility = View.GONE
            } else {
                goLive?.visibility = View.GONE
                exoPosition?.visibility = View.VISIBLE
                exoDuration?.visibility = View.VISIBLE
            }
        }
    }
}