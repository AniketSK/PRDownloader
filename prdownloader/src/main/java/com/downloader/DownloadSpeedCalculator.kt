package com.downloader

import java.util.*
import java.util.concurrent.TimeUnit

class DownloadSpeedCalculator(private val maxElements: Int = 2, private val onSpeedUpdated: (Long?) -> (Unit)) : TimeStampedProgressListener(2, TimeUnit.SECONDS) {
    private val allProgressEvents = ArrayDeque<TimeStampedProgress>(maxElements)

    override fun onTimeStampedProgress(timeStampedProgress: TimeStampedProgress) {
        allProgressEvents.apply {
            push(timeStampedProgress)
            if (size > maxElements) {
                removeLast()
            }
        }
        onSpeedUpdated(getAverageSpeedBytesPerMillisecond())
    }

    /**
     * Returns the current speed if it has received the specified minimum number of Progress values to
     * make a reasonable speed determination,
     * or null if enough Progress values have not yet been received.
     */
    private fun getAverageSpeedBytesPerMillisecond(): Long? = allProgressEvents.takeIf {
        it.size == maxElements
    }?.let {
        allProgressEvents.last.progress.currentBytes - allProgressEvents.first.progress.currentBytes / (allProgressEvents.last.lastTimeStamp - allProgressEvents.first.lastTimeStamp)
    }

}
