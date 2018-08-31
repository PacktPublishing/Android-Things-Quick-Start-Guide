package com.plattysoft.pwmsamples

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.google.android.things.pio.PeripheralManager
import com.google.android.things.pio.Pwm

/**
 * Created by Raul Portales on 13/05/18.
 */
class LedBrightnessActivity : Activity() {

    private val initialTime = System.currentTimeMillis()
    private lateinit var pwm: Pwm
    private val handler = Handler()

    private val ledRunnable = object : Runnable {
        override fun run() {
            val elapsedSeconds = (System.currentTimeMillis() - initialTime) / 1000.0
            val dutyCycle = Math.cos(elapsedSeconds) * 50.0 + 50
            pwm.setPwmDutyCycle(dutyCycle)
            handler.post(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pwm = PeripheralManager.getInstance().openPwm(BoardDefaults.servoPwm)
        pwm.setPwmFrequencyHz(50.0)
        pwm.setEnabled(true)

        handler.post(ledRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(ledRunnable)
        pwm.close()
    }
}