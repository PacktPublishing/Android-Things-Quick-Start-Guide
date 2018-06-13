package com.plattysoft.gpiosamples

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.things.contrib.driver.tm1637.NumericDisplay
import com.google.android.things.userdriver.UserDriverManager
import com.leinardi.android.things.driver.hcsr04.Hcsr04
import com.leinardi.android.things.driver.hcsr04.Hcsr04SensorDriver
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by Raul Portales on 13/05/18.
 */
class DistanceSensorDriverActivity : Activity() {
    companion object {
        private const val DEVICE_RPI3 = "rpi3"
        private const val DEVICE_IMX7D_PICO = "imx7d_pico"

        val triggerGpio: String
            get() = when (Build.DEVICE) {
                DEVICE_RPI3 -> "BCM23"
                DEVICE_IMX7D_PICO -> "GPIO2_IO13"
                else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
            }
        val echoGpio: String
            get() = when (Build.DEVICE) {
                DEVICE_RPI3 -> "BCM24"
                DEVICE_IMX7D_PICO -> "GPIO2_IO12"
                else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
            }
    }

    val sensorCallback = object : SensorManager.DynamicSensorCallback() {
        override fun onDynamicSensorConnected(sensor: Sensor?) {
            if (sensor?.type == Sensor.TYPE_PROXIMITY) {
                sensorManager.registerListener(sensorListener,
                        sensor, SensorManager.SENSOR_DELAY_FASTEST);
            }
        }
    }

    val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val distance = event.values[0]
            Log.i(ContentValues.TAG, "proximity changed: " + distance)
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.i(ContentValues.TAG, "accuracy changed: $accuracy")
        }
    }

    lateinit var sensorManager : SensorManager
    lateinit var sensorDriver: Hcsr04SensorDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorDriver = Hcsr04SensorDriver(triggerGpio, echoGpio)

        sensorManager.registerDynamicSensorCallback(sensorCallback)
        sensorDriver.registerProximitySensor()
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterDynamicSensorCallback(sensorCallback)
        sensorDriver.unregisterProximitySensor()
    }
}
