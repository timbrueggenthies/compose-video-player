package de.brueggenthies.compose.video.ui.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import kotlinx.coroutines.delay

@Composable
fun VideoControlsContainer(
    playbackState: MutableVideoPlayerState,
    visible: Boolean,
    modifier: Modifier = Modifier,
    enterTransition: EnterTransition = fadeIn(),
    exitTransition: ExitTransition = fadeOut(),
    overlay: @Composable BoxScope.(MutableVideoPlayerState) -> Unit = { },
    content: @Composable VideoControlsComponentsScope.(MutableVideoPlayerState) -> Unit,
) {
    Box(modifier = modifier) {
        Box(modifier = Modifier.matchParentSize()) {
            overlay(playbackState)
        }
        AnimatedVisibility(visible = visible, enter = enterTransition, exit = exitTransition) {
            val innerScope = VideoControlsComponentsScopeImpl(this@Box, this)
            Box(modifier = Modifier.matchParentSize()) {
                content(innerScope, playbackState)
            }
        }
    }
}

@Composable
fun VideoControlsContainer(
    playbackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier,
    enterTransition: EnterTransition = fadeIn(),
    exitTransition: ExitTransition = fadeOut(),
    overlay: @Composable BoxScope.(MutableVideoPlayerState) -> Unit = { },
    content: @Composable VideoControlsComponentsScope.(MutableVideoPlayerState) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var controlsVisible by remember { mutableStateOf(false) }
    VideoControlsContainer(
        modifier = modifier
            .clickable(interactionSource, null) { controlsVisible = !controlsVisible },
        playbackState = playbackState,
        visible = controlsVisible,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        overlay = overlay,
        content = content
    )
    LaunchedEffect(key1 = controlsVisible) {
        if (controlsVisible) {
            delay(5000)
            controlsVisible = false
        }
    }
}

interface VideoControlsComponentsScope : BoxScope, AnimatedVisibilityScope

class VideoControlsComponentsScopeImpl(
    boxScope: BoxScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) : VideoControlsComponentsScope, BoxScope by boxScope, AnimatedVisibilityScope by animatedVisibilityScope
