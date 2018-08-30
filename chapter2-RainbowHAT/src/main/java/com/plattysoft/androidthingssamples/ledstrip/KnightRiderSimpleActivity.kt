package com.plattysoft.androidthingssamples.ledstrip

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import java.util.*
import kotlin.concurrent.timerTask


/**
 * Created by Raul Portales on 06/05/18.
 */
class KnightRiderSimpleActivity : Activity() {
    private val timer: Timer = Timer()
    private var goingUp = true
    private var currentPos = 0
    private val interval: Long = 100

    val colors = IntArray(RainbowHat.LEDSTRIP_LENGTH)
    private lateinit var ledStrip: Apa102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ledStrip = RainbowHat.openLedStrip()
        ledStrip.brightness = Apa102.MAX_BRIGHTNESS
        ledStrip.direction = Apa102.Direction.NORMAL

        timer.schedule(timerTask {
            knightRider()
        }, 0, interval)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        ledStrip.close()
    }

    private fun knightRider() {
        updateCurrentPos()
        updateLedStrip()
    }

    private fun updateCurrentPos() {
        if (goingUp) {
            currentPos++
            if (currentPos == RainbowHat.LEDSTRIP_LENGTH - 1) {
                goingUp = false
            }
        } else {
            currentPos--
            if (currentPos == 0) {
                goingUp = true
            }
        }
    }

    private fun updateLedStrip() {
        for (i in colors.indices) {
            if (i == currentPos) {
                colors[i] = Color.RED
            } else {
                colors[i] = Color.TRANSPARENT
            }
        }
        ledStrip.write(colors)
    }
}