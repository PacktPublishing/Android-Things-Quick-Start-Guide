package com.plattysoft.androidthingssamples.temperatureDisplay

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.bmx280.Bmx280

/**
 * Created by Raul Portales on 07/05/18.
 */
class PressureDisplayActivity: Activity() {

    private val handler = Handler()

    private lateinit var sensor: Bmx280
    private lateinit var alphanumericDisplay: AlphanumericDisplay

    val displayPressureRunnable = object: Runnable {
        override fun run() {
            val pressure = sensor.readPressure().toDouble()
            alphanumericDisplay.display(pressure)
            handler.postDelayed(this, 1000)
        }
    }
    // Use the RainbowUtil.getWeatherStripColors() helper to convert the pressure value into a color array for the Apa102 driver.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensor = RainbowHat.openSensor()
        sensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        sensor.pressureOversampling = Bmx280.OVERSAMPLING_1X

        alphanumericDisplay = RainbowHat.openDisplay()
        alphanumericDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        alphanumericDisplay.setEnabled(true)

        handler.post(displayPressureRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensor.close()
        alphanumericDisplay.close()
    }
}