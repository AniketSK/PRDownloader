package com.downloader

import org.junit.Test

class OnProgressSpeedListenerTest {

    @Test
    fun `speed is not calculated until the required number of samples have been received`() {
        var speed : Long? = null
        val speedCalc = DownloadSpeedCalculator(2, onSpeedUpdated = { s -> speed = s })
        val times = 1
        repeat(times) { i -> speedCalc.onTimeStampedProgress(TimeStampedProgress(i * 500L, Progress(i * 1000L, times * 10000L))) }

        assert(speed == null)
    }

    @Test
    fun `speed is calculated correctly`() {
        var speed : Long? = 0L
        val speedCalc = DownloadSpeedCalculator(2, onSpeedUpdated = { s -> speed = s })
        val times = 2
        repeat(times) { i -> speedCalc.onTimeStampedProgress(TimeStampedProgress(i * 500L, Progress(i * 1000L, times * 10000L))) }
        assert(speed == 2L)
    }
}


