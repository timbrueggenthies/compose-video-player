package de.brueggenthies.compose.video.internal

import androidx.media3.common.Player
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import de.brueggenthies.compose.video.state.RepeatMode
import de.brueggenthies.compose.video.state.VideoPlayerState

internal class ActualVideoPlayerState(
    private val player: Player,
    private val backingVideoPlayerState: VideoPlayerState
) : MutableVideoPlayerState {
    override val duration by backingVideoPlayerState::duration
    override val currentPosition by backingVideoPlayerState::currentPosition
    override val bufferedPosition by backingVideoPlayerState::bufferedPosition
    override val bufferedPercentage by backingVideoPlayerState::bufferedPercentage
    override val videoSize by backingVideoPlayerState::videoSize
    override val playbackState by backingVideoPlayerState::playbackState

    override var isPlaying: Boolean
        get() = backingVideoPlayerState.isPlaying
        set(value) {
            if (value) player.play() else player.pause()
        }
    override var repeatMode: RepeatMode
        get() = backingVideoPlayerState.repeatMode
        set(value) {
            player.repeatMode = value.exoPlayerValue
        }

    override fun seekToPosition(position: Long) {
        if (!player.isCurrentMediaItemSeekable) return
        player.seekTo(position)
    }

    override fun seekToPercentage(percentage: Float) {
        if (!player.isCurrentMediaItemSeekable) return
        val clamped = percentage.coerceIn(0f, 1f)
        val seekTo = duration?.let { (it * clamped).toLong() } ?: 0L
        player.seekTo(seekTo)
    }
}