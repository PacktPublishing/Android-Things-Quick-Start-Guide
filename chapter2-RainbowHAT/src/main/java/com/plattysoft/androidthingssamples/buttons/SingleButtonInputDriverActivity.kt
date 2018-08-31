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
class SingleButtonInputDriverActivity : Activity() {

    private lateinit var redLed: Gpio
    private lateinit var buttonInputDriverA: ButtonInputDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = RainbowHat.openLedRed()
        buttonInputDriverA = RainbowHat.createButtonAInputDriver(KeyEvent.KEYCODE_A)
        buttonInputDriverA.register()
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

    override fun onDestroy() {
        super.onDestroy()
        redLed.close()
        buttonInputDriverA.unregister()
    }
}