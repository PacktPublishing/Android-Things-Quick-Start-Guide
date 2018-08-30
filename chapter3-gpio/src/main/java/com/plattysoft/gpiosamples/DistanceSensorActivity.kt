package com.plattysoft.gpiosamples

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.leinardi.android.things.driver.hcsr04.Hcsr04

/**
 * Created by Raul Portales on 13/05/18.
 */
class DistanceSensorActivity : Activity() {
    companion object {
        private const val triggerGpio = "BCM23"
        private const val echoGpio = "BCM24"
    }

    private lateinit var sensor: Hcsr04
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensor = Hcsr04(triggerGpio, echoGpio)

        handler.post(object : Runnable {
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
