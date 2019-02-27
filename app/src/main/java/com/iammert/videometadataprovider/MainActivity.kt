package com.iammert.videometadataprovider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iammert.library.videometadataprovider.VideoDataProviderDelegator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = VideoDataProviderDelegator(this)
        provider.fetchVideoData(
                videoPath = "/storage/emulated/0/DCIM/Camera/VID_20190226_150621.mp4",
                onSuccess = {
                    Log.v("TEST", "video: ${it.videoHeight}")
                },
                onFail = {

                })
    }
}
