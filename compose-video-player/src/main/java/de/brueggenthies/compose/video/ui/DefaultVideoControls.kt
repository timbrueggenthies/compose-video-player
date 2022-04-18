package de.brueggenthies.compose.video.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import de.brueggenthies.compose.video.ui.components.AnimatedSeekbar
import de.brueggenthies.compose.video.ui.core.VideoControlsContainer
import kotlinx.coroutines.delay

@Composable
fun DefaultVideoControls(
    playbackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier,
    background: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit = {
        defaultBackground(it)
    },
    playPauseButton: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit = {
        defaultPlayPauseButton(it)
    },
    seekbar: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit = {
        defaultSeekBar(this, it)
    },
) {
    val interactionSource = remember { MutableInteractionSource() }
    var controlsVisible by remember { mutableStateOf(false) }
    VideoControlsContainer(
        playbackState = playbackState,
        modifier = modifier
            .fillMaxSize()
            .clickable(interactionSource, null) { controlsVisible = !controlsVisible }
    ) {
        background(playbackState)
        Box(modifier = Modifier.align(Alignment.Center)) {
            playPauseButton(this@VideoControls, playbackState)
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            seekbar(this@VideoControls, playbackState)
        }
    }
    LaunchedEffect(key1 = controlsVisible) {
        if (controlsVisible) {
            delay(5000)
            controlsVisible = false
        }
    }
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
        PlayPauseButtonWithLoading(videoPlaybackState = it)
    }

private val defaultSeekBar: @Composable AnimatedVisibilityScope.(MutableVideoPlayerState) -> Unit = {
    AnimatedSeekbar(videoPlaybackState = it)
}
