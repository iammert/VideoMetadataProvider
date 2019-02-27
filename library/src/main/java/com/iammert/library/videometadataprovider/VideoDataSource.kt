package com.iammert.library.videometadataprovider

data class VideoDataSource(val videoPath: String,
                           val videoTotalDuration: Long,
                           var videoWidth: Float,
                           var videoHeight: Float,
                           var videoRotation: Int)