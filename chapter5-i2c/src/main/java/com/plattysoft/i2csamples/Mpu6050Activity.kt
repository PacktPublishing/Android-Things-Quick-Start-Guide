package com.plattysoft.i2csamples

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.plattysoft.mpu6050.Mpu6050

/**
 * Created by Raul Portales on 16/05/18.
 */
class Mpu6050Activity : Activity() {

    companion object {
        private const val TAG = "Mpu6050Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gyroscope = Mpu6050.open()
        // Values are on accelXYZ and gyroXYZ
        Log.d(TAG, "Accel: x:${gyroscope.accelX} y:${gyroscope.accelY} z:${gyroscope.accelZ}")
        Log.d(TAG, "Gyro: x:${gyroscope.gyroX} y:${gyroscope.gyroY} z:${gyroscope.gyroZ}")
        gyroscope.close()
    }
}