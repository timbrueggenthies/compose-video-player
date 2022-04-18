package de.brueggenthies.compose.video.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import de.brueggenthies.compose.video.state.VideoPlayerState

@Composable
private fun NonInteractiveTimeline(videoPlayerState: VideoPlayerState) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .height(4.dp)
            .fillMaxWidth()
            .clip(CircleShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.4f))
        )
        val animatedBufferedPercentage by animateFloatAsState(targetValue = videoPlayerState.bufferedPercentage)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedBufferedPercentage)
                .background(Color.White)
        )
        videoPlayerState.duration?.let { duration ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(videoPlayerState.currentPosition / duration.toFloat())
                    .background(Color.Red)
            )
        }
    }
}

@Composable
fun Seekbar(
    videoPlaybackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.pointerInput(Unit) { handleScrollbarTouch(videoPlaybackState) }) {
        NonInteractiveTimeline(videoPlayerState = videoPlaybackState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityScope.AnimatedSeekbar(
    videoPlaybackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier
) {
    val offset by transition.animateDp(label = "Offset of SeekBar") { enterExitState ->
        when (enterExitState) {
            EnterExitState.PreEnter -> 32.dp
            EnterExitState.Visible -> 0.dp
            EnterExitState.PostExit -> 32.dp
        }
    }
    Seekbar(
        videoPlaybackState = videoPlaybackState,
        modifier = modifier
            .offset(y = offset)
    )
}

private suspend fun PointerInputScope.handleScrollbarTouch(videoPlaybackState: MutableVideoPlayerState) {
    forEachGesture {
        awaitPointerEventScope {
            val down = awaitFirstDown()
            down.consumeDownChange()
            videoPlaybackState.isPlaying = false
            while (true) {
                val next = awaitDragOrCancellation(down.id)
                if (next == null) {
                    videoPlaybackState.isPlaying = true
                    return@awaitPointerEventScope
                } else {
                    videoPlaybackState.seekToPercentage(next.position.x / size.width)
                }
            }
        }
    }
}
