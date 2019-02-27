package com.iammert.library.videometadataprovider.providers

import com.iammert.library.videometadataprovider.VideoDataProvider
import com.iammert.library.videometadataprovider.VideoDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wseemann.media.FFmpegMediaMetadataRetriever
import java.lang.Exception

internal class FFMPEGVideoMetaDataProvider : VideoDataProvider {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun fetchVideoData(videoPath: String, onSuccess: (VideoDataSource) -> Unit, onFail: (Throwable) -> Unit) {

        uiScope.launch {
            try {
                val videoDataSource = withContext(Dispatchers.IO) {
                    val mediaMetadataRetriever = FFmpegMediaMetadataRetriever()
                    mediaMetadataRetriever.setDataSource(videoPath)
                    val totalDuration = mediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
                    val videoWidth = mediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toFloat()
                    val videoHeight = mediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toFloat()
                    val videoRotation = mediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION).toInt()
                    mediaMetadataRetriever.release()
                    VideoDataSource(
                            videoPath = videoPath,
                            videoTotalDuration = totalDuration,
                            videoWidth = videoWidth,
                            videoHeight = videoHeight,
                            videoRotation = videoRotation)
                }
                onSuccess.invoke(videoDataSource)
            } catch (e: Exception) {
                onFail.invoke(e)
            }
        }
    }
}