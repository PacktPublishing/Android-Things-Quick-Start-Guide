package com.plattysoft.i2csamples

import android.os.Build

object BoardDefaults {
    private const val DEVICE_RPI3 = "rpi3"
    private const val DEVICE_IMX7D_PICO = "imx7d_pico"

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
}
