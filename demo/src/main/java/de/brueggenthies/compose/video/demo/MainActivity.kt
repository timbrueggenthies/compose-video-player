package de.brueggenthies.compose.video.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import de.brueggenthies.compose.video.VideoPlaybackSurface
import de.brueggenthies.compose.video.aspectRatioOfVideo
import de.brueggenthies.compose.video.demo.ui.theme.ComposeVideoPlayerTheme
import de.brueggenthies.compose.video.rememberVideoPlayerState
import de.brueggenthies.compose.video.state.RepeatMode
import de.brueggenthies.compose.video.ui.DefaultVideoControls

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVideoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    VideoDemo()
                }
            }
        }
    }

    @Composable
    private fun VideoDemo() {
        val context = LocalContext.current
        val player = remember {
            val player = ExoPlayer.Builder(context).build()
            val firstItem =
                MediaItem.fromUri("https://content.videvo.net/videvo_files/video/free/2021-04/originalContent/210329_06B_Bali_1080p_013.mp4")
            val secondItem =
                MediaItem.fromUri("https://assets.mixkit.co/videos/download/mixkit-portrait-of-a-fashion-woman-with-silver-makeup-39875.mp4")
            player.setMediaItem(firstItem)
            player.addMediaItem(secondItem)
            player.prepare()
            player
        }
        val playbackState = rememberVideoPlayerState(player = player)
        LaunchedEffect(playbackState) {
            playbackState.repeatMode = RepeatMode.All
            playbackState.isPlaying = true
        }
        Box(
            modifier = Modifier
                .aspectRatioOfVideo(videoPlayerState = playbackState)
                .animateContentSize()
        ) {
            VideoPlaybackSurface(
                player = player,
                modifier = Modifier
                    .aspectRatioOfVideo(videoPlayerState = playbackState)
            )
            DefaultVideoControls(playbackState = playbackState, modifier = Modifier.fillMaxSize())
        }
    }
}
