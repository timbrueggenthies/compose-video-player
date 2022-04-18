package de.brueggenthies.compose.video.internal

import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import de.brueggenthies.compose.video.internal.VideoPlayerStateHolder
import de.brueggenthies.compose.video.internal.intSizeOrNull
import de.brueggenthies.compose.video.state.PlaybackState
import de.brueggenthies.compose.video.state.RepeatMode

internal class PlayerListener(private val videoPlayerState: VideoPlayerStateHolder) : Player.Listener {
    override fun onEvents(player: Player, events: Player.Events) {
        if (events.contains(Player.EVENT_TIMELINE_CHANGED)) {
            videoPlayerState.duration = player.duration
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        this.videoPlayerState.playbackState = PlaybackState.ofExoplayerValue(playbackState)
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        videoPlayerState.repeatMode = RepeatMode.ofExoplayerValue(repeatMode)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        videoPlayerState.isPlaying = isPlaying
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        videoPlayerState.videoSize = videoSize.intSizeOrNull()
    }
}