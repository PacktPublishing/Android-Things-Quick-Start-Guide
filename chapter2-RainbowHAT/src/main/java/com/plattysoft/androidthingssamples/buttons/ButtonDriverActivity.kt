package com.plattysoft.androidthingssamples.buttons

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

/**
 * Created by Raul Portales on 28/04/18.
 */
class ButtonDriverActivity : Activity() {

    private lateinit var redLed: Gpio
    private lateinit var greenLed: Gpio
    private lateinit var blueLed: Gpio

    private lateinit var buttonA: Button
    private lateinit var buttonB: Button
    private lateinit var buttonC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = RainbowHat.openLedRed()
        greenLed = RainbowHat.openLedGreen()
        blueLed = RainbowHat.openLedBlue()

        buttonA = RainbowHat.openButtonA()
        buttonB = RainbowHat.openButtonB()
        buttonC = RainbowHat.openButtonC()

        buttonA.setOnButtonEventListener { button: Button, state: Boolean ->
            redLed.value = state
        }
        buttonB.setOnButtonEventListener { button: Button, state: Boolean ->
            greenLed.value = state
        }
        buttonC.setOnButtonEventListener { button: Button, state: Boolean ->
            blueLed.value = state
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        redLed.close()
        greenLed.close()
        blueLed.close()

        buttonA.close()
        buttonB.close()
        buttonC.close()
    }
}