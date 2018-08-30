package com.plattysoft.gpiosamples

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_A
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager

/**
 * Created by Raul Portales on 28/04/18.
 */
class ButtonInputDriverActivity : Activity() {

    private lateinit var redLed: Gpio

    private lateinit var buttonDriverA: ButtonInputDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = PeripheralManager.getInstance().openGpio(BoardDefaults.ledR)
        redLed.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)

        buttonDriverA = ButtonInputDriver(BoardDefaults.buttonA, Button.LogicState.PRESSED_WHEN_LOW, KEYCODE_A)
        buttonDriverA.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        redLed.close()
        buttonDriverA.close()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_A) {
            redLed.value = true
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_A) {
            redLed.value = false
            return true
        } else {
            return super.onKeyUp(keyCode, event)
        }
    }
}