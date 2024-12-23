package meow.softer.yuyuan.data.repository.music

import android.content.res.AssetFileDescriptor

/**
 * Responsible for playing music
 */

// Data class to represent the playback state
data class MusicState(
    val isPlaying: Boolean = false,
    val currentPosition: Int = 0,  // Current playback position in milliseconds
    val totalDuration: Int = 0,    // Total duration of the track in milliseconds
    val trackName: String = ""     // Name of the current track
)

interface IMusicRepository {
    suspend fun setSource(source: String)
    suspend fun play()
    suspend fun pause()
    suspend fun stop()
    fun release()
    suspend fun seekTo(position: Int)
    suspend fun updatePlaybackPosition()
    suspend fun setSource(afd: AssetFileDescriptor)
}