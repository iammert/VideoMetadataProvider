package com.iammert.library.videometadataprovider

interface VideoDataProvider {
    fun fetchVideoData(videoPath: String, onSuccess: (VideoDataSource) -> Unit, onFail: (Throwable) -> Unit)
}