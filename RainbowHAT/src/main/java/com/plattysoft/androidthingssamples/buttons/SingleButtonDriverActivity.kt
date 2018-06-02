package com.plattysoft.androidthingssamples.buttons

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

/**
 * Created by Raul Portales on 28/04/18.
 */
class SingleButtonDriverActivity : Activity() {

    private lateinit var redLed: Gpio

    private lateinit var buttonA: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = RainbowHat.openLedRed()

        buttonA = RainbowHat.openButtonA()
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