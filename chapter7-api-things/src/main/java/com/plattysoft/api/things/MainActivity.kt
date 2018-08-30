package com.plattysoft.api.things

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import org.json.JSONObject

private val TAG = MainActivity::class.java.simpleName

class MainActivity : Activity(), ApiListener {

    lateinit var redLed: Gpio
    lateinit var temperatureSensor: Bmx280

    lateinit var apiServer: ApiServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = RainbowHat.openLedRed()

        temperatureSensor = RainbowHat.openSensor()
        temperatureSensor.setMode(Bmx280.MODE_NORMAL)
        temperatureSensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X

        apiServer = ApiServer(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiServer.stop()
        redLed.close()
        temperatureSensor.close()
    }

    override fun onGetTemperature(): JSONObject {
        val response = JSONObject()
        val value = temperatureSensor.readTemperature().toDouble()
        response.put("temperature", value)
        return response
    }

    override fun onPostLed(request: JSONObject) {
        val status = request.get("status") as Boolean
        redLed.value = status
    }
}
