package com.plattysoft.androidthingssamples.led

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

private val TAG = BlinkThreadActivity::class.java.simpleName

class BlinkThreadActivity : Activity() {

    private lateinit var led: Gpio

    private val thread = Thread {
        while(true) {
            loop()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
        thread.start()
    }

    private fun setup() {
        led = RainbowHat.openLedGreen()
    }

    private fun loop() {
        led.value = !led.value
        Thread.sleep(1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        thread.interrupt()
        led.close()
    }

}
