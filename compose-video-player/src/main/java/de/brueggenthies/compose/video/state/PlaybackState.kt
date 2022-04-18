package de.brueggenthies.compose.video.state

import androidx.media3.common.Player

enum class PlaybackState {
    Idle,
    Ready,
    Buffering,
    Ended;

    internal companion object {
        fun ofExoplayerValue(@Player.State value: Int): PlaybackState {
            return when (value) {
                Player.STATE_IDLE -> Idle
                Player.STATE_READY -> Ready
                Player.STATE_BUFFERING -> Buffering
                Player.STATE_ENDED -> Ended
                else -> error("Unknown player state: $value")
            }
        }
    }
}
