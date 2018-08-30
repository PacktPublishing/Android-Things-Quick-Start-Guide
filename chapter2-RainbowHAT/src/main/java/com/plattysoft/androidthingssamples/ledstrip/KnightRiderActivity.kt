package com.plattysoft.androidthingssamples.ledstrip

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import java.util.*
import kotlin.concurrent.timerTask


/**
 * Created by Raul Portales on 06/05/18.
 */
class KnightRiderActivity : Activity() {

    companion object {
        private val MIN_INTERVAL = 25
        private val MAX_INTERVAL = 400
    }

    private var timer: Timer? = null
    private var goingUp = true
    private var currentPos = 0
    private var interval: Long = 200

    private lateinit var inputDriverA: ButtonInputDriver
    private lateinit var inputDriverB: ButtonInputDriver
    private lateinit var inputDriverC: ButtonInputDriver

    private lateinit var ledStrip: Apa102

    private val colors = IntArray(RainbowHat.LEDSTRIP_LENGTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alphanumericDisplay = RainbowHat.openDisplay()
        alphanumericDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        alphanumericDisplay.setEnabled(true)
        alphanumericDisplay.display("KITT")
        alphanumericDisplay.close()

        ledStrip = RainbowHat.openLedStrip()
        ledStrip.brightness = 31
        ledStrip.direction = Apa102.Direction.NORMAL

        inputDriverA = RainbowHat.createButtonAInputDriver(KeyEvent.KEYCODE_A)
        inputDriverA.register()
        inputDriverB = RainbowHat.createButtonBInputDriver(KeyEvent.KEYCODE_B)
        inputDriverB.register()
        inputDriverC = RainbowHat.createButtonCInputDriver(KeyEvent.KEYCODE_C)
        inputDriverC.register()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_A) {
            if (timer == null) {
                startTimer()
            } else {
                stopTimer()
            }
        } else if (keyCode == KeyEvent.KEYCODE_B) {
            if (interval > MIN_INTERVAL) {
                interval = interval / 2
                restartTimer()
            }
        } else if (keyCode == KeyEvent.KEYCODE_C) {
            if (interval < MAX_INTERVAL) {
                interval = interval * 2
                restartTimer()
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()

        inputDriverA.unregister()
        inputDriverB.unregister()
        inputDriverC.unregister()

        ledStrip.close()
    }

    private fun restartTimer() {
        stopTimer()
        startTimer()
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    private fun startTimer() {
        timer = Timer()
        timer!!.schedule(timerTask {
            knightRider()
        }, interval, interval)
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