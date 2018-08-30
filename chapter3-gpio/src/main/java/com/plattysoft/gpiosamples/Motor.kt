package com.plattysoft.gpiosamples

import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager

class Motor(gpioForwardPin: String, gpioBackwardPin: String) : AutoCloseable {
    private val gpioForward: Gpio
    private val gpioBackward: Gpio

    init {
        val service = PeripheralManager.getInstance()

        gpioForward = service.openGpio(gpioForwardPin)
        gpioForward.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        gpioBackward = service.openGpio(gpioBackwardPin)
        gpioBackward.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
    }

    override fun close() {
        gpioForward.close()
        gpioBackward.close()
    }

    fun forward() {
        gpioForward.value = true
        gpioBackward.value = false
    }

    fun backward() {
        gpioForward.value = false
        gpioBackward.value = true
    }

    fun stop() {
        gpioForward.value = false
        gpioBackward.value = false
    }
}
