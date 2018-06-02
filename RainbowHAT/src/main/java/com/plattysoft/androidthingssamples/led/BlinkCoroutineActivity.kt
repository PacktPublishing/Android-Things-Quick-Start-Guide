package com.plattysoft.androidthingssamples.led

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import kotlinx.coroutines.experimental.*

private val TAG = BlinkCoroutineActivity::class.java.simpleName

class BlinkCoroutineActivity : Activity() {

    private lateinit var led: Gpio
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
        job = launch {
            while(isActive) {
                loop()
            }
        }
    }

    private fun setup() {
        led = RainbowHat.openLedGreen()
    }

    private suspend fun loop() {
        led.value = !led.value
        delay(1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        led.close()
    }
}
