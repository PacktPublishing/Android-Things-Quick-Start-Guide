package com.plattysoft.i2csamples

import android.app.Activity
import android.os.Bundle
import android.util.Log

/**
 * Created by Raul Portales on 17/06/18.
 */
class ScanI2cActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defaultBus = I2cUtils.getBus()
        val activeAddresses = I2cUtils.scanAvailableAddresses(defaultBus)
        for (address in activeAddresses) {
            Log.d("ScanI2cActivity", "Address found: 0x" + address.toString(16))
        }
    }
}