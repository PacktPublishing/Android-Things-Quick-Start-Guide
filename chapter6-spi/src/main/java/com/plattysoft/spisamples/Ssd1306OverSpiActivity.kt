package com.plattysoft.spisamples

import android.app.Activity
import android.os.Bundle
import com.plattysoft.androidthings.ssd1306.Ssd1306


/**
 * Created by Raul Portales on 30/05/18.
 */
class Ssd1306OverSpiActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = Ssd1306.openSpi("SPI0.0", "BCM25", "BCM24", 128, 64)
        for (i in 0 until display.lcdWidth) {
            for (j in 0 until display.lcdHeight) {
                display.setPixel(i, j, i % display.lcdHeight > j)
            }
        }
        // Render the pixel data
        display.show()
        // Cleanup
        display.close()
    }
}