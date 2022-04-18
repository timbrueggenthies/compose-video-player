package de.brueggenthies.compose.video.internal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import androidx.media3.common.Player
import de.brueggenthies.compose.video.internal.durationOrNull
import de.brueggenthies.compose.video.internal.intSizeOrNull
import de.brueggenthies.compose.video.state.PlaybackState
import de.brueggenthies.compose.video.state.RepeatMode
import de.brueggenthies.compose.video.state.VideoPlayerState

internal class VideoPlayerStateHolder(player: Player) : VideoPlayerState {
    override var isPlaying: Boolean by mutableStateOf(player.isPlaying)
    override var currentPosition: Long by mutableStateOf(player.currentPosition)
    override var duration: Long? by mutableStateOf(player.duration.durationOrNull())
    override var bufferedPosition: Long by mutableStateOf(player.bufferedPosition)
    override var bufferedPercentage: Float by mutableStateOf(player.bufferedPercentage / 100f)
    override var repeatMode: RepeatMode by mutableStateOf(RepeatMode.ofExoplayerValue(player.repeatMode))
    override var videoSize: IntSize? by mutableStateOf(player.videoSize.intSizeOrNull())
    override var playbackState: PlaybackState by mutableStateOf(PlaybackState.ofExoplayerValue(player.playbackState))
}