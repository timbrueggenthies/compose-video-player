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
import androidx.compose.foundation.layout.fillMaxSize
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
    content: @Composable VideoControlsComponentsScope.(MutableVideoPlayerState) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = visible, enter = enterTransition, exit = exitTransition) {
            val scope = VideoControlsComponentsScopeImpl(this@Box, this)
            Box(modifier = Modifier.fillMaxSize()) {
                content(scope, playbackState)
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
    content: @Composable VideoControlsComponentsScope.(MutableVideoPlayerState) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var controlsVisible by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .clickable(interactionSource, null) { controlsVisible = !controlsVisible }
    ) {
        VideoControlsContainer(
            playbackState = playbackState,
            visible = controlsVisible,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            content = content
        )
    }
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
    animatedVisibilityScope: AnimatedVisibilityScope
) : VideoControlsComponentsScope, BoxScope by boxScope, AnimatedVisibilityScope by animatedVisibilityScope
