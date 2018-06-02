package com.plattysoft.androidthingssamples.led

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import java.util.*
import kotlin.concurrent.timerTask

class BlinkTimerActivity : Activity() {

    private lateinit var led: Gpio
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        led = RainbowHat.openLedGreen()
        timer.schedule ( timerTask {
            led.value = !led.value
        }, 0, 1000 )
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        timer.purge()
        led.close()
    }

}
