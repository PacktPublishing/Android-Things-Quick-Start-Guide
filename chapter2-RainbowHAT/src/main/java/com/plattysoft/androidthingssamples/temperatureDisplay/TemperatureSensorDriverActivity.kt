package com.plattysoft.androidthingssamples.temperatureDisplay

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

/**
 * Created by Raul Portales on 07/05/18.
 */
class TemperatureSensorDriverActivity : Activity() {

    private val sensorCallback = object : SensorManager.DynamicSensorCallback() {
        override fun onDynamicSensorConnected(sensor: Sensor?) {
            if (sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                registerTemperatureListener(sensor)
            }
        }
    }

    private val temperatureSensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            Log.i(TAG, "temperature changed: ${event.values[0]}")
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.i(TAG, "accuracy changed: $accuracy")
        }
    }

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorDriver: Bmx280SensorDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerDynamicSensorCallback(sensorCallback)

        sensorDriver = RainbowHat.createSensorDriver()
        sensorDriver.registerTemperatureSensor()
    }

    private fun registerTemperatureListener(sensor: Sensor) {
        sensorManager.registerListener(temperatureSensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterDynamicSensorCallback(sensorCallback)
        sensorManager.unregisterListener(temperatureSensorListener)
        sensorDriver.unregisterTemperatureSensor()
        sensorDriver.close()
    }
}
