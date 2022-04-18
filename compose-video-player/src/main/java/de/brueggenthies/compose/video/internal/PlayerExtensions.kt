package de.brueggenthies.compose.video.internal

import androidx.compose.ui.unit.IntSize
import androidx.media3.common.C
import androidx.media3.common.VideoSize

internal fun Long.durationOrNull(): Long? = if (this == C.TIME_UNSET) null else this

internal fun VideoSize.intSizeOrNull(): IntSize? = if (height == 0 && width == 0) null else IntSize(width, height)
