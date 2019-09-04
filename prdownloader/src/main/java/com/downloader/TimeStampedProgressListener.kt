package com.downloader

import java.util.concurrent.TimeUnit

private const val INITIAL = 0L
abstract class TimeStampedProgressListener(updateInterval: Long, updateIntervalUnit: TimeUnit) : OnProgressListener {

    private val intervalMillis = TimeUnit.MILLISECONDS.convert(updateInterval, updateIntervalUnit)
    private var lastUpdateTime: Long = INITIAL

    final override fun onProgress(progress: Progress?) {
        defaultConditionalProgress(progress)
    }

    private fun defaultConditionalProgress(progress: Progress?) {

        if (progress == null)
            return

        val now = now()
        if (now - lastUpdateTime >= intervalMillis) {
            lastUpdateTime = now
            onTimeStampedProgress(TimeStampedProgress(now, progress))
        }
    }

    internal abstract fun onTimeStampedProgress(timeStampedProgress: TimeStampedProgress)

    private fun now(): Long = System.currentTimeMillis()
}