package com.plattysoft.i2csamples

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.plattysoft.pca9685.PCA9685
import com.plattysoft.pca9685.ServoUnderPca9685

/**
 * Created by Raul Portales on 09/06/18.
 */
class Pca9685ServoActivity : Activity() {
    private lateinit var buttonA: ButtonInputDriver
    private lateinit var buttonB: ButtonInputDriver
    private lateinit var buttonC: ButtonInputDriver
    private lateinit var pca9685: PCA9685
    private lateinit var servo: ServoUnderPca9685
    private lateinit var display: AlphanumericDisplay

    private var targetAngle = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pca9685 = PCA9685()
        servo = pca9685.openServo(0)

        servo.setPulseDurationRange(0.65, 2.5)
        servo.setAngleRange(0.0, 180.0)

        display = AlphanumericDisplay(I2cUtils.getBus())
        display.setEnabled(true)
        display.setBrightness(AlphanumericDisplay.HT16K33_BRIGHTNESS_MAX)

        buttonA = ButtonInputDriver(BoardDefaults.buttonA, Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_A)
        buttonB = ButtonInputDriver(BoardDefaults.buttonB, Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_B)
        buttonC = ButtonInputDriver(BoardDefaults.buttonC, Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_C)

        buttonA.register()
        buttonB.register()
        buttonC.register()
    }

    override fun onDestroy() {
        super.onDestroy()

        pca9685.close()
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
            KeyEvent.KEYCODE_A -> {
                decreaseAngle()
                return true
            }
            KeyEvent.KEYCODE_B -> {
                increaseAngle()
                return true
            }
            KeyEvent.KEYCODE_C -> {
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