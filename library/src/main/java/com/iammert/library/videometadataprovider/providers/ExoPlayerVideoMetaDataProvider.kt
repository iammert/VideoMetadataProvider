package com.iammert.library.videometadataprovider.providers

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

import com.iammert.library.videometadataprovider.VideoDataProvider
import com.iammert.library.videometadataprovider.VideoDataSource
import java.io.File

internal class ExoPlayerVideoMetaDataProvider(private val context: Context) : VideoDataProvider, Player.EventListener {

    private val metaDataPlayer: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context).apply {
        playWhenReady = false
        addListener(this@ExoPlayerVideoMetaDataProvider)
    }

    private var onSuccessListener: ((VideoDataSource) -> Unit)? = null
    private var onFailListener: ((Throwable) -> Unit)? = null
    private lateinit var videoPath: String

    private var width: Int = 0
    private var height: Int = 0
    private var rotation: Int = 0
    private var duration: Long = 0L

    override fun fetchVideoData(videoPath: String, onSuccess: (VideoDataSource) -> Unit, onFail: (Throwable) -> Unit) {
        this.onSuccessListener = onSuccess
        this.onFailListener = onFail
        this.videoPath = videoPath
        val mediaSource = createVideoSource(context, videoPath)
        metaDataPlayer.prepare(mediaSource)
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        val format = trackGroups?.get(0)?.getFormat(0)
        if (format != null) {
            width = format.width
            height = format.height
            rotation = format.rotationDegrees
        } else {
            onFailListener?.invoke(Throwable("Can not handle video metadata by ExoPlayer"))
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == STATE_READY) {
            duration = metaDataPlayer.duration
            val videoDataSource = VideoDataSource(
                    videoPath = videoPath,
                    videoTotalDuration = duration,
                    videoWidth = width.toFloat(),
                    videoHeight = height.toFloat(),
                    videoRotation = rotation)
            metaDataPlayer.stop()
            metaDataPlayer.release()
            onSuccessListener?.invoke(videoDataSource)
        }
    }

    companion object {

        private fun createVideoSource(context: Context, videoPath: String): MediaSource {
            val defaultDataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "metaDataRetriever"))
            return ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.fromFile(File(videoPath)))
        }
    }

}