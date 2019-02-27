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

## Download
```gradle
maven { url 'https://jitpack.io' }
```

```gradle
dependencies {
        implementation 'com.github.iammert:VideoMetadataProvider:0.1'
}
```

License
--------


    Copyright 2019 Mert Şimşek.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




