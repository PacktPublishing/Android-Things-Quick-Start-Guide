package com.plattysoft.androidthingssamples.led

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

class BlinkLoopActivity : Activity() {

    private lateinit var led: Gpio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
        while (true) {
            loop()
        }
    }

    private fun setup() {
        led = RainbowHat.openLedRed()
    }

    private fun loop() {
        led.value = !led.value
        Thread.sleep(1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        led.close()
    }

}
