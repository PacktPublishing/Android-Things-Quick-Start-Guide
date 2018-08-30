package com.plattysoft.androidthingssamples

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.PeripheralManager
import com.google.android.things.pio.Pwm
import com.plattysoft.pwmsamples.BoardDefaults

/**
 * Created by Raul Portales on 29/04/18.
 */
class PianoPwmActivity : Activity() {

    private lateinit var buttonA: ButtonInputDriver
    private lateinit var buttonB: ButtonInputDriver
    private lateinit var buttonC: ButtonInputDriver

    private lateinit var buzzer: Pwm

    private val frequencies = hashMapOf(
            KEYCODE_A to 1000.0,
            KEYCODE_B to 3000.0,
            KEYCODE_C to 5000.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pioService = PeripheralManager.getInstance()
        buzzer = pioService.openPwm(BoardDefaults.piezoPwm)
        buzzer.setPwmDutyCycle(50.0)

        buttonA = ButtonInputDriver(BoardDefaults.buttonA, Button.LogicState.PRESSED_WHEN_LOW, KEYCODE_A)
        buttonB = ButtonInputDriver(BoardDefaults.buttonB, Button.LogicState.PRESSED_WHEN_LOW, KEYCODE_B)
        buttonC = ButtonInputDriver(BoardDefaults.buttonC, Button.LogicState.PRESSED_WHEN_LOW, KEYCODE_B)

        buttonA.register()
        buttonB.register()
        buttonC.register()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val freqToPlay = frequencies.get(keyCode)
        if (freqToPlay != null) {
            buzzer.setPwmFrequencyHz(freqToPlay)
            buzzer.setEnabled(true)
            return true
        } else {
            return false
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        buzzer.setEnabled(false)
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