package com.plattysoft.androidthingssamples.buzzer

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.contrib.driver.pwmspeaker.Speaker
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

/**
 * Created by Raul Portales on 29/04/18.
 */
class PianoActivity : Activity() {

    private lateinit var buttonA: ButtonInputDriver
    private lateinit var buttonB: ButtonInputDriver
    private lateinit var buttonC: ButtonInputDriver

    private lateinit var buzzer: Speaker

    private val frequencies = hashMapOf(
            KEYCODE_A to 1000.0,
            KEYCODE_B to 3000.0,
            KEYCODE_C to 5000.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buzzer = RainbowHat.openPiezo()

        buttonA = RainbowHat.createButtonAInputDriver(KEYCODE_A)
        buttonB = RainbowHat.createButtonBInputDriver(KEYCODE_B)
        buttonC = RainbowHat.createButtonCInputDriver(KEYCODE_C)

        buttonA.register()
        buttonB.register()
        buttonC.register()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val freqToPlay = frequencies.get(keyCode)
        if (freqToPlay != null) {
            buzzer.play(freqToPlay)
            return true
        }
        else {
            return false
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        buzzer.stop()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        buzzer.close()

        buttonA.unregister()
        buttonB.unregister()
        buttonC.unregister()
    }
}