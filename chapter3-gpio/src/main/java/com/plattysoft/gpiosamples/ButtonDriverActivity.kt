package com.plattysoft.gpiosamples

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager

/**
 * Created by Raul Portales on 28/04/18.
 */
class ButtonDriverActivity : Activity() {

    private lateinit var redLed: Gpio
    private lateinit var buttonA: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = PeripheralManager.getInstance().openGpio(BoardDefaults.ledR)
        redLed.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)

        buttonA = Button(BoardDefaults.buttonA, Button.LogicState.PRESSED_WHEN_LOW)
        buttonA.setOnButtonEventListener { button: Button, state: Boolean ->
            redLed.value = state
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        redLed.close()
        buttonA.close()
    }
}