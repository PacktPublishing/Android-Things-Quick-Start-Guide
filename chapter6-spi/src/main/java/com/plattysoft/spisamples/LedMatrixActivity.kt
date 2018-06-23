package com.plattysoft.spisamples

import android.app.Activity
import android.os.Bundle
import com.nilhcem.androidthings.driver.max72xx.LedControl
import java.util.*
import kotlin.concurrent.timerTask

class LedMatrixActivity : Activity() {

    private val SPI_BUS: String = BoardDefaults.spiHatBus

    private var currentSprite = 0
    val sprites = arrayOf(
            byteArrayOf(
                0b00011000.toByte(),
                0b00111100.toByte(),
                0b01111110.toByte(),
                0b11011011.toByte(),
                0b11111111.toByte(),
                0b00100100.toByte(),
                0b01011010.toByte(),
                0b10100101.toByte()
            ),
            byteArrayOf (
                0b00011000.toByte(),
                0b00111100.toByte(),
                0b01111110.toByte(),
                0b11011011.toByte(),
                0b11111111.toByte(),
                0b01011010.toByte(),
                0b10000001.toByte(),
                0b01000010.toByte()
            )
    )

    private lateinit var ledControl: LedControl
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ledControl = LedControl(SPI_BUS, 1) // second parameter is the number of chained matrices. Here, we only use 1 LED matrix module (8x8).
        initLedMatrix()
        timer.schedule(timerTask {
            swapSprite()
        }, 1000, 1000)
    }

    private fun initLedMatrix() {
        for (i in 0 until ledControl.getDeviceCount()) {
            ledControl.setIntensity(i, 1)
            ledControl.shutdown(i, false)
            ledControl.clearDisplay(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        timer.purge()
        ledControl.close()
    }

    private fun swapSprite() {
        currentSprite++
        for (row in 0..7) {
            ledControl.setRow(0, row, sprites[currentSprite%sprites.size][row])
        }
    }
}
