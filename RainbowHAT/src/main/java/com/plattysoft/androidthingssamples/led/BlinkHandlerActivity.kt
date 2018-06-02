package com.plattysoft.androidthingssamples.led

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

class BlinkHandlerActivity : Activity() {

    private lateinit var led: Gpio
    private val handler = Handler()

    private val ledRunnable = object: Runnable {
        override fun run() {
            led.value = !led.value
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        led = RainbowHat.openLedGreen()
        handler.post(ledRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(ledRunnable)
        led.close()
    }

}
