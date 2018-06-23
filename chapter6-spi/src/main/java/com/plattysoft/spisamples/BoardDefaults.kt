package com.plattysoft.spisamples

import android.os.Build

object BoardDefaults {
    private const val DEVICE_RPI3 = "rpi3"
    private const val DEVICE_IMX7D_PICO = "imx7d_pico"

    val spiBus: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "SPI0.0"
            DEVICE_IMX7D_PICO -> "SPI3.1"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
    val spiHatBus: String
        get() = when (Build.DEVICE) {
            DEVICE_RPI3 -> "SPI0.1"
            DEVICE_IMX7D_PICO -> "SPI3.0"
            else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
        }
}

