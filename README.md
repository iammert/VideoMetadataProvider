# VideoMetadataProvider
Video metadata provider library (collect metadata from ExoPlayer, FFMpeg, Native Android)

Supported providers;
* ExoPlayer
* FFMpeg Metadata
* Android Native Metadata

The library tries to fetch metadata information from providers. If first provider fails to fetch data, 
then It tries the next one. onFail callback is called when all providers can not fetch required metadata.

VideoData has following attributes;

* Total video duration
* Video original width and height value
* Video rotation degree

## Usage

```kotlin
val provider = VideoDataProviderDelegator(this)
provider.fetchVideoData(
        videoPath = "VIDEO_PATH",
        onSuccess = { videoData -> },
        onFail = { throwable -> })
```
