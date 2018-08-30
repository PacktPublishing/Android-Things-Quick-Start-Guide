package com.plattysoft.gpiosamples

import android.app.Activity
import android.os.Bundle
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager

/**
 * Created by Raul Portales on 28/04/18.
 */
class ButtonGpioActivity : Activity() {

    private lateinit var redLed: Gpio
    private lateinit var buttonA: Gpio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = PeripheralManager.getInstance().openGpio(BoardDefaults.ledR)
        redLed.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)

        buttonA = PeripheralManager.getInstance().openGpio(BoardDefaults.buttonA)
        buttonA.setDirection(Gpio.DIRECTION_IN)
        buttonA.setActiveType(Gpio.ACTIVE_LOW)
        buttonA.setEdgeTriggerType(Gpio.EDGE_BOTH)
        buttonA.registerGpioCallback {
            redLed.value = it.value
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        redLed.close()
        buttonA.close()
    }
}