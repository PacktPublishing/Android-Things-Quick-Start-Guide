package com.plattysoft.androidthingssamples.lcd


import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

/**
 * Created by rportales on 03/05/2017.
 */

class Marquee : AutoCloseable {

    companion object {
        private val MARQUEE_INTERVAL: Long = 800
        private val LCD_LENGTH = 4
        val PADDING = "    "
    }

    private var timer: Timer? = null
    private var currentMarqueePos: Int = 0
    private var marqueeText: String = ""

    private val display: AlphanumericDisplay

    init {
        display = RainbowHat.openDisplay()
        display.setEnabled(true)
        display.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
    }

    override fun close() {
        stop()
        display.close()
    }

    fun stop() {
        if (timer != null) {
            timer!!.cancel()
            timer!!.purge()
            timer = null
        }
    }

    fun displayText(text: String) {
        marqueeText = PADDING + text + PADDING
        currentMarqueePos = 0
        stop()
        timer = Timer()
        timer!!.schedule(timerTask {
            updateMarquee()
        }, 0, MARQUEE_INTERVAL)
    }

    private fun updateMarquee() {
        // In this display, the character '.' does not count as such (unless consecutive)
        // The length of the string to dispay depends on the number of dots there
        if (marqueeText[currentMarqueePos] == '.') {
            currentMarqueePos++
        }
        val displayText = splitString(marqueeText, currentMarqueePos)
        display.display(displayText)

        currentMarqueePos++
        if (currentMarqueePos + displayText.length >= marqueeText.length) {
            currentMarqueePos = 0
        }
    }

    private fun splitString(string: String, startPos: Int): String {
        var currentPos = startPos
        for (numProperCharacters in 0 until LCD_LENGTH) {
            // A dot after the current character is considered part of the character
            if (string.length > currentPos + 1 && string[currentPos + 1] == '.') {
                currentPos++
            }
            currentPos++
        }
        return string.substring(startPos, currentPos)
    }
}