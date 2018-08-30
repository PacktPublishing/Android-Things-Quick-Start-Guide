package com.plattysoft.gpiosamples

import android.app.Activity
import android.os.Build
import android.os.Bundle
import com.google.android.things.contrib.driver.tm1637.NumericDisplay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by Raul Portales on 12/05/18.
 */
class LCDClockActivity : Activity() {

    companion object {
        private const val DEVICE_RPI3 = "rpi3"
        private const val DEVICE_IMX7D_PICO = "imx7d_pico"

        private val dataGpioPinName: String
            get() = when (Build.DEVICE) {
                DEVICE_RPI3 -> "BCM23"
                DEVICE_IMX7D_PICO -> "GPIO2_IO13"
                else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
            }
        private val clockGpioPinName: String
            get() = when (Build.DEVICE) {
                DEVICE_RPI3 -> "BCM24"
                DEVICE_IMX7D_PICO -> "GPIO2_IO12"
                else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
            }
    }

    private val dateFormat = SimpleDateFormat("HHmm")
    private val date = Date()

    lateinit var display: NumericDisplay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        display = NumericDisplay(dataGpioPinName, clockGpioPinName)
        display.setBrightness(NumericDisplay.MAX_BRIGHTNESS)

        Timer().schedule(timerTask {
            // Blink the colon
            display.colonEnabled = !display.colonEnabled
            // Update the values
            date.time = System.currentTimeMillis()
            display.display(dateFormat.format(date))
        }, 0, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        display.close()
    }
}