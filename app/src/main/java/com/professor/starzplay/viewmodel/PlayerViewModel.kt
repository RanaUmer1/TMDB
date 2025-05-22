package com.professor.starzplay.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.professor.data_source.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Rana Umer on 5/22/2025.
 *
 * Description: Application
 *
 * @version 1.0
 */


@HiltViewModel
class PlayerViewModel @Inject constructor(
    application: Application
) : ViewModel() {

    val player: ExoPlayer = ExoPlayer.Builder(application).build()

    private val mediaItem = MediaItem.fromUri(Constants.VIDEO_LINK)

    fun preparePlayerIfNeeded() {
        if (player.mediaItemCount == 0) {
            player.setMediaItem(mediaItem)
            player.prepare()
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
