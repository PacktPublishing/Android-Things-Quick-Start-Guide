package com.plattysoft.androidthingssamples.temperatureDisplay

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.bmx280.Bmx280

/**
 * Created by Raul Portales on 07/05/18.
 */
class TemperatureDisplayActivity: Activity() {

    private val handler = Handler()

    private lateinit var sensor: Bmx280
    private lateinit var alphanumericDisplay: AlphanumericDisplay

    val displayTemperatureRunnable = object: Runnable {
        override fun run() {
            val temperature = sensor.readTemperature().toDouble()
            // Display the temperature on the alphanumeric display.
            alphanumericDisplay.display(temperature)
            handler.post(this )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensor = RainbowHat.openSensor()
        sensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        sensor.setMode(Bmx280.MODE_NORMAL)

        alphanumericDisplay = RainbowHat.openDisplay()
        alphanumericDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        alphanumericDisplay.setEnabled(true)

        handler.post(displayTemperatureRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(displayTemperatureRunnable)
        sensor.close()
        alphanumericDisplay.close()
    }
}