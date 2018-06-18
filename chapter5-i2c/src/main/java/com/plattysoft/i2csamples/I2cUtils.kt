package com.plattysoft.i2csamples

import com.google.android.things.pio.PeripheralManager
import java.io.IOException
import java.util.*

/**
 * Created by Raul Portales on 26/05/18.
 */
object I2cUtils {

    fun getBus(): String {
        val peripheralManager = PeripheralManager.getInstance()
        val deviceList = peripheralManager.i2cBusList
        return if (deviceList.isEmpty()) {
            "I2C1"
        } else {
            deviceList[0]
        }
    }

    fun scanAvailableAddresses(i2cName: String): List<Int> {
        val pm = PeripheralManager.getInstance()
        val availableAddresses = mutableListOf<Int>()
        for (address in 0..127) {
            val device = pm.openI2cDevice(i2cName, address)
            try {
                device.write(ByteArray(1), 1)
                availableAddresses.add(address)
            } catch (e: IOException) {
                // Not available, not adding it
            } finally {
                device.close()
            }
        }
        return availableAddresses
    }
}