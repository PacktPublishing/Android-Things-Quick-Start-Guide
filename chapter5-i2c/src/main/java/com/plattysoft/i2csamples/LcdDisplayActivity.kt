package com.plattysoft.i2csamples

import android.app.Activity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import com.nilhcem.androidthings.driver.lcdpcf8574.LcdPcf8574



/**
 * Created by Raul Portales on 05/06/18.
 */
class LcdDisplayActivity : Activity() {

    companion object {
        const val I2C_ADDRESS = 0x27
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lcd = LcdPcf8574(I2cUtils.getBus(), I2C_ADDRESS)
        lcd.begin(16, 2)
        lcd.setBacklight(true)

        lcd.clear()
        lcd.print("Hello,")
        lcd.setCursor(0, 1)
        lcd.print("Android Things!")

        lcd.close()
    }
}
