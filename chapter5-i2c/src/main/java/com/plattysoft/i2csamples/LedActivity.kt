package com.plattysoft.i2csamples

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.google.android.things.pio.PeripheralManager
import com.plattysoft.pca9685.PCA9685
import com.plattysoft.pcf8591.Pcf8591

/**
 * Created by Raul Portales on 23/05/18.
 */
class LedActivity: Activity() {

    private lateinit var pwmExpander: PCA9685
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pwmExpander = PCA9685();
        pwmExpander.setPwmFreq(50)
        val initialTime = System.currentTimeMillis()

        handler.post(object : Runnable {
            override fun run() {
                val elapsedSeconds = (System.currentTimeMillis() - initialTime) / 1000.0
                val dutyCycle = Math.cos(elapsedSeconds) * 50.0 + 50
                pwmExpander.setPwmDutyCycle(12, dutyCycle.toInt())
                handler.post(this)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        pwmExpander.close()
    }
}