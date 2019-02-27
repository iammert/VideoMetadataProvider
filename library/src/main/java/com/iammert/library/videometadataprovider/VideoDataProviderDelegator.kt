package com.iammert.library.videometadataprovider

import android.content.Context
import com.iammert.library.videometadataprovider.providers.AndroidVideoMetaDataProvider
import com.iammert.library.videometadataprovider.providers.ExoPlayerVideoMetaDataProvider
import com.iammert.library.videometadataprovider.providers.FFMPEGVideoMetaDataProvider

class VideoDataProviderDelegator(context: Context) : VideoDataProvider {

    private val delegates = arrayListOf(
            ExoPlayerVideoMetaDataProvider(context),
            AndroidVideoMetaDataProvider(),
            FFMPEGVideoMetaDataProvider()
    )

    private var onSuccessListener: ((VideoDataSource) -> Unit)? = null
    private var onFailListener: ((Throwable) -> Unit)? = null

    override fun fetchVideoData(videoPath: String, onSuccess: (VideoDataSource) -> Unit, onFail: (Throwable) -> Unit) {
        this.onSuccessListener = onSuccess
        this.onFailListener = onFail
        execute(videoPath = videoPath, position = 0)
    }

    private fun execute(videoPath: String, position: Int) {
        delegates[position]
                .fetchVideoData(
                        videoPath = videoPath,
                        onSuccess = { videoDataSource -> onSuccessListener?.invoke(videoDataSource) },
                        onFail = {
                            if (position == delegates.size - 1) {
                                onFailListener?.invoke(Throwable("None of the provider can handle this video metadata"))
                            } else {
                                execute(videoPath = videoPath, position = position + 1)
                            }
                        })

    }
}