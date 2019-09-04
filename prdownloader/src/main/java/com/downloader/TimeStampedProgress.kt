package com.downloader

internal data class TimeStampedProgress(
        val lastTimeStamp: Long = 0,
        val progress: Progress
)