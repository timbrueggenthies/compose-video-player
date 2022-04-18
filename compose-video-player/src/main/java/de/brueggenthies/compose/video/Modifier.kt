package de.brueggenthies.compose.video

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import de.brueggenthies.compose.video.state.VideoPlayerState

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.aspectRatioOfVideo(
    videoPlayerState: VideoPlayerState,
    defaultAspectRatio: Float = 16f / 9f,
    minAspectRatio: Float = 21f / 9f,
    maxAspectRatio: Float = 10f
): Modifier = composed {
    val aspectRatio = videoPlayerState.videoSize?.run { width.toFloat() / height.toFloat() } ?: defaultAspectRatio
    aspectRatio(aspectRatio.coerceIn(minAspectRatio, maxAspectRatio))
}
