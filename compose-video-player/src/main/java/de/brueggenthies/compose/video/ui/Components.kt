package de.brueggenthies.compose.video.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import de.brueggenthies.compose.video.R
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import de.brueggenthies.compose.video.state.PlaybackState

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun PlayPauseButton(
    videoPlaybackState: MutableVideoPlayerState,
    modifier: Modifier = Modifier,
    enabled: Boolean = videoPlaybackState.playbackState != PlaybackState.Idle
) {
    val vector = AnimatedImageVector.animatedVectorResource(id = R.drawable.avd_anim)
    val icon = rememberAnimatedVectorPainter(animatedImageVector = vector, atEnd = enabled && !videoPlaybackState.isPlaying)
    val animatedAlpha by animateFloatAsState(targetValue = if (enabled) 1f else 0.5f)
    Image(
        painter = icon,
        colorFilter = ColorFilter.tint(Color.White),
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .alpha(animatedAlpha)
            .clickable(enabled = enabled) { videoPlaybackState.isPlaying = !videoPlaybackState.isPlaying }
            .padding(8.dp)
            .size(48.dp)
    )
}
