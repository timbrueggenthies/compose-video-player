package de.brueggenthies.compose.video.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.IntSize

@Stable
interface VideoPlayerState {
    val isPlaying: Boolean
    val currentPosition: Long
    val duration: Long?
    val bufferedPosition: Long
    val bufferedPercentage: Float
    val repeatMode: RepeatMode
    val videoSize: IntSize?
    val playbackState: PlaybackState
}

@Stable
interface MutableVideoPlayerState : VideoPlayerState {
    override var isPlaying: Boolean
    override var repeatMode: RepeatMode
    fun seekToPosition(position: Long)
    fun seekToPercentage(percentage: Float)
}

