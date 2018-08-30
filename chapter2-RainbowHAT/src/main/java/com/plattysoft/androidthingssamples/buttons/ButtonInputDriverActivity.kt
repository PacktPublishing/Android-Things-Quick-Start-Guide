package com.plattysoft.androidthingssamples.buttons

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

/**
 * Created by Raul Portales on 28/04/18.
 */
class ButtonInputDriverActivity : Activity() {

    private lateinit var redLed: Gpio
    private lateinit var greenLed: Gpio
    private lateinit var blueLed: Gpio

    private lateinit var buttonA: ButtonInputDriver
    private lateinit var buttonB: ButtonInputDriver
    private lateinit var buttonC: ButtonInputDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = RainbowHat.openLedRed()
        greenLed = RainbowHat.openLedGreen()
        blueLed = RainbowHat.openLedBlue()

        buttonA = RainbowHat.createButtonAInputDriver(KeyEvent.KEYCODE_A)
        buttonB = RainbowHat.createButtonBInputDriver(KeyEvent.KEYCODE_B)
        buttonC = RainbowHat.createButtonCInputDriver(KeyEvent.KEYCODE_C)

        buttonA.register()
        buttonB.register()
        buttonC.register()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val led = getLedForKeycode(keyCode)
        if (led != null) {
            led.value = true
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val led = getLedForKeycode(keyCode)
        if (led != null) {
            led.value = false
            return true
        } else {
            return super.onKeyUp(keyCode, event)
        }
    }

    private fun getLedForKeycode(keyCode: Int): Gpio? {
        when (keyCode) {
            KEYCODE_A -> {
                return redLed
            }
            KEYCODE_B -> {
                return greenLed
            }
            KEYCODE_C -> {
                return blueLed
            }
            else -> {
                return null
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        redLed.close()
        greenLed.close()
        blueLed.close()

        buttonA.unregister()
        buttonB.unregister()
        buttonC.unregister()
    }
}