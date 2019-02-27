package com.iammert.library.videometadataprovider.providers

import android.media.MediaMetadataRetriever
import com.iammert.library.videometadataprovider.VideoDataProvider
import com.iammert.library.videometadataprovider.VideoDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


internal class AndroidVideoMetaDataProvider : VideoDataProvider {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun fetchVideoData(videoPath: String, onSuccess: (VideoDataSource) -> Unit, onFail: (Throwable) -> Unit) {
        uiScope.launch {
            try {
                val videoDataSource = withContext(Dispatchers.IO) {
                    val mediaMetadataRetriever = MediaMetadataRetriever()
                    mediaMetadataRetriever.setDataSource(videoPath)
                    val totalDuration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
                    val videoWidth = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toFloat()
                    val videoHeight = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toFloat()
                    val videoRotation = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION).toInt()
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
                onFail.invoke(Throwable("Can not read metadata from Android Native Metadata"))
            }
        }
    }
}