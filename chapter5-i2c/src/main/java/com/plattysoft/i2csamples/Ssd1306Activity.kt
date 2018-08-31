package com.plattysoft.i2csamples

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.ssd1306.Ssd1306

/**
 * Created by Raul Portales on 30/05/18.
 */
class Ssd1306Activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = Ssd1306(I2cUtils.getBus())
        for (i in 0 until display.lcdWidth) {
            for (j in 0 until display.lcdHeight) {
                display.setPixel(i, j, i % display.lcdHeight > j)
            }
        }
        // render the pixel data
        display.show()
        // Cleanup
        display.close()
    }
}