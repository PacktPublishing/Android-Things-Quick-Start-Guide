package com.plattysoft.androidui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.CompoundButton
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    lateinit var redLed: Gpio
    lateinit var temperatureSensor: Bmx280

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redLed = RainbowHat.openLedRed()

        temperatureSensor = RainbowHat.openSensor()
        temperatureSensor.setMode(Bmx280.MODE_NORMAL)
        temperatureSensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X

        configureLedSwitch()

        configureTemperatureReading()
    }

    override fun onDestroy() {
        super.onDestroy()
        redLed.close()
        temperatureSensor.close()
    }

    private fun configureLedSwitch() {
        redLedSwitch.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            redLed.value = b
        }
    }

    private fun configureTemperatureReading() {
        handler.post(object : Runnable {
            override fun run() {
                val temperature = temperatureSensor.readTemperature()
                temperatureValue.text = temperature.toString()
                handler.postDelayed(this, 1000)
            }
        })
    }
}
