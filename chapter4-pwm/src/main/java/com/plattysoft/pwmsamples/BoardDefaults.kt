package com.plattysoft.pwmsamples

import android.os.Build

object BoardDefaults {
    private const val DEVICE_RPI3 = "rpi3"
    private const val DEVICE_IMX7D_PICO = "imx7d_pico"

    val i2cBus: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "I2C1"
            DEVICE_IMX7D_PICO -> "I2C1"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val spiBus: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "SPI0.0"
            DEVICE_IMX7D_PICO -> "SPI3.1"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val piezoPwm: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "PWM1"
            DEVICE_IMX7D_PICO -> "PWM2"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val servoPwm: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "PWM0"
            DEVICE_IMX7D_PICO -> "PWM1"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val buttonA: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "BCM21"
            DEVICE_IMX7D_PICO -> "GPIO6_IO14"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val buttonB: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "BCM20"
            DEVICE_IMX7D_PICO -> "GPIO6_IO15"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val buttonC: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "BCM16"
            DEVICE_IMX7D_PICO -> "GPIO2_IO07"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val ledR: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "BCM6"
            DEVICE_IMX7D_PICO -> "GPIO2_IO02"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val ledG: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "BCM19"
            DEVICE_IMX7D_PICO -> "GPIO2_IO00"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val ledB: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "BCM26"
            DEVICE_IMX7D_PICO -> "GPIO2_IO05"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
}

