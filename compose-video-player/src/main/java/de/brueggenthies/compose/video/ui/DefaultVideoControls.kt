package de.brueggenthies.compose.video.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import de.brueggenthies.compose.video.state.PlaybackState
import de.brueggenthies.compose.video.ui.components.AnimatedSeekbar
import de.brueggenthies.compose.video.ui.core.VideoControlsContainer

@Composable
fun DefaultVideoControls(
    playbackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier,
    editor: VideoControlsEditorScope.() -> Unit = { },
) {
    val editorScope = remember { VideoControlsEditorScope(defaultSeekBar, defaultPlayPauseButton, defaultBackground, defaultLoadingIndicator) }
    editorScope.editor()
    VideoControlsContainer(
        playbackState = playbackState,
        modifier = modifier,
        overlay = {
            if (it.playbackState == PlaybackState.Buffering) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    editorScope.loadingIndicator(playbackState)
                }
            }
        },
        content = {
            editorScope.background(this, playbackState)
            if (it.playbackState != PlaybackState.Buffering) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    editorScope.playPauseButton(this@VideoControlsContainer, playbackState)
                }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                editorScope.seekbar(this@VideoControlsContainer, playbackState)
            }
        }
    )
}

private val defaultBackground: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit =
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )
    }

private val defaultPlayPauseButton: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit =
    {
        PlayPauseButton(videoPlaybackState = it)
    }

private val defaultSeekBar: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit = {
    AnimatedSeekbar(videoPlaybackState = it)
}

private val defaultLoadingIndicator: @Composable (MutableVideoPlayerState) -> Unit = {
    CircularProgressIndicator(color = Color.White)
}

class VideoControlsEditorScope internal constructor(
    seekbar: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit,
    playPauseButton: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit,
    background: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit,
    loadingIndicator: @Composable (MutableVideoPlayerState) -> Unit
) {
    var seekbar: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit by mutableStateOf(seekbar)
    var playPauseButton: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit by mutableStateOf(playPauseButton)
    var background: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit by mutableStateOf(background)
    var loadingIndicator: @Composable (MutableVideoPlayerState) -> Unit by mutableStateOf(loadingIndicator)
}
