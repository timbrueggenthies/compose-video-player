package de.brueggenthies.compose.video.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import de.brueggenthies.compose.video.state.PlaybackState

@Composable
fun PlayPauseButton(
    videoPlaybackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier
) {
    val color = if (videoPlaybackState.isPlaying) Color.Red else Color.Green
    Box(
        modifier = modifier
            .size(24.dp)
            .background(color = color)
            .clickable { videoPlaybackState.isPlaying = !videoPlaybackState.isPlaying }
    )
}

@Composable
fun PlayPauseButtonWithLoading(
    videoPlaybackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (videoPlaybackState.playbackState) {
            PlaybackState.Idle -> { /* Show nothing, player needs to be prepared */
            }
            PlaybackState.Ready -> PlayPauseButton(videoPlaybackState = videoPlaybackState)
            PlaybackState.Buffering -> CircularProgressIndicator(color = Color.White)
            PlaybackState.Ended -> PlayPauseButton(videoPlaybackState = videoPlaybackState)
        }
    }
}
