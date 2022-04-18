package de.brueggenthies.compose.video

import android.view.SurfaceView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.Player
import de.brueggenthies.compose.video.internal.ActualVideoPlayerState
import de.brueggenthies.compose.video.internal.PlayerListener
import de.brueggenthies.compose.video.internal.VideoPlayerStateHolder
import de.brueggenthies.compose.video.state.MutableVideoPlayerState
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.isActive

@Composable
fun rememberVideoPlayerState(
    player: Player,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): MutableVideoPlayerState {
    val playbackState = remember(player) { VideoPlayerStateHolder(player) }
    val actualPlaybackState = remember(player) { ActualVideoPlayerState(player, playbackState) }
    DisposableEffect(key1 = player) {
        val listener = PlayerListener(playbackState)
        player.addListener(listener)
        onDispose { player.removeListener(listener) }
    }
    LaunchedEffect(key1 = Unit) {
        while (isActive) {
            awaitFrame()
            playbackState.currentPosition = player.currentPosition
            playbackState.bufferedPosition = player.bufferedPosition
            playbackState.bufferedPercentage = player.bufferedPercentage / 100f
        }
    }
    DisposableEffect(key1 = lifecycleOwner) {
        val observer: LifecycleObserver = object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                actualPlaybackState.isPlaying = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    return actualPlaybackState
}

@Composable
fun VideoPlaybackSurface(player: Player, modifier: Modifier = Modifier) {
    var surfaceView: SurfaceView? by remember { mutableStateOf(null) }
    AndroidView(modifier = modifier, factory = { context -> SurfaceView(context) }) {
        surfaceView = it
    }
    DisposableEffect(surfaceView) {
        val currentSurfaceView = surfaceView
        player.setVideoSurfaceView(currentSurfaceView)
        onDispose {
            player.clearVideoSurfaceView(currentSurfaceView)
        }
    }
}
