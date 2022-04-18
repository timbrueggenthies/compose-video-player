package de.brueggenthies.compose.video.state

import androidx.media3.common.Player

/**
 * RepeatMode
 *
 * Create by adesso mobile solutions GmbH on 05.04.2022
 */
enum class RepeatMode(@Player.RepeatMode internal val exoPlayerValue: Int) {
    Off(Player.REPEAT_MODE_OFF), One(Player.REPEAT_MODE_ONE), All(Player.REPEAT_MODE_ALL);

    internal companion object {
        fun ofExoplayerValue(@Player.RepeatMode value: Int): RepeatMode {
            return when (value) {
                Player.REPEAT_MODE_OFF -> Off
                Player.REPEAT_MODE_ONE -> One
                Player.REPEAT_MODE_ALL -> All
                else -> error("Unknown repeat mode: $value")
            }
        }
    }
}
