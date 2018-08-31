package com.plattysoft.pwmsamples

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.pwmservo.Servo

/**
 * Created by Raul Portales on 13/05/18.
 */
class ServoActivity : Activity() {

    private lateinit var buttonA: ButtonInputDriver
    private lateinit var buttonB: ButtonInputDriver
    private lateinit var buttonC: ButtonInputDriver
    private lateinit var servo: Servo
    private lateinit var display: AlphanumericDisplay

    private var targetAngle = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        servo = Servo(BoardDefaults.servoPwm)
        servo.setPulseDurationRange(0.65, 2.5)
        servo.setAngleRange(0.0, 180.0)
        servo.setEnabled(true)

        display = AlphanumericDisplay(BoardDefaults.i2cBus)
        display.setEnabled(true)
        display.setBrightness(AlphanumericDisplay.HT16K33_BRIGHTNESS_MAX)

        buttonA = ButtonInputDriver(BoardDefaults.buttonA, Button.LogicState.PRESSED_WHEN_LOW, KEYCODE_A)
        buttonB = ButtonInputDriver(BoardDefaults.buttonB, Button.LogicState.PRESSED_WHEN_LOW, KEYCODE_B)
        buttonC = ButtonInputDriver(BoardDefaults.buttonC, Button.LogicState.PRESSED_WHEN_LOW, KEYCODE_C)

        buttonA.register()
        buttonB.register()
        buttonC.register()
    }

    override fun onDestroy() {
        super.onDestroy()

        servo.close()
        display.close()

        buttonA.unregister()
        buttonB.unregister()
        buttonC.unregister()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (handleKeyCode(keyCode)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent?): Boolean {
        if (handleKeyCode(keyCode)) {
            return true
        }
        return super.onKeyMultiple(keyCode, repeatCount, event)
    }

    private fun handleKeyCode(keyCode: Int): Boolean {
        when (keyCode) {
            KEYCODE_A -> {
                decreaseAngle()
                return true
            }
            KEYCODE_B -> {
                increaseAngle()
                return true
            }
            KEYCODE_C -> {
                servo.angle = targetAngle
                return true
            }
            else -> {
                return false
            }
        }
    }

    private fun decreaseAngle() {
        targetAngle--
        if (targetAngle < servo.minimumAngle) {
            targetAngle = servo.minimumAngle
        }
        display.display(targetAngle)
    }

    private fun increaseAngle() {
        targetAngle++
        if (targetAngle > servo.maximumAngle) {
            targetAngle = servo.maximumAngle
        }
        display.display(targetAngle)
    }
}