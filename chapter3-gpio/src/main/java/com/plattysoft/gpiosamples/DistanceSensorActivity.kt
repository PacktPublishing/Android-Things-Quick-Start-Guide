package com.plattysoft.gpiosamples

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.things.contrib.driver.tm1637.NumericDisplay
import com.leinardi.android.things.driver.hcsr04.Hcsr04
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by Raul Portales on 13/05/18.
 */
class DistanceSensorActivity : Activity() {
    companion object {
        val triggerGpio = "BCM23"
        val echoGpio = "BCM24"
    }

    lateinit var sensor: Hcsr04
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensor = Hcsr04(triggerGpio, echoGpio)

        handler.post(object: Runnable {
            override fun run() {
                val distance = sensor.readDistance()
                Log.d("DistanceSensorActivity", "distance: $distance")
                handler.post(this)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        sensor.close()
    }

}
