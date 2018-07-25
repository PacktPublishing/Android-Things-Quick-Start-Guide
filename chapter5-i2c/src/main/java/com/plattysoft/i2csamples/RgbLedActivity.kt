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
class RgbLedActivity: Activity() {

    private lateinit var pwmExpander: PCA9685
    private lateinit var adc: Pcf8591
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adc = Pcf8591.open()
        pwmExpander = PCA9685()

        handler.post(object : Runnable {
            override fun run() {
                val values = adc.readAllValues()
                for (i in 0..2) {
                    pwmExpander.setPwmDutyCycle(i, (values[i] / 256.0).toInt())
                }
                handler.postDelayed(this, 200)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        adc.close()
        pwmExpander.close()
    }
}