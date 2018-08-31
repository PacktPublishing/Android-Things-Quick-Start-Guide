package com.plattysoft.nearby.things

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes.STATUS_OK
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import org.json.JSONObject

class MainActivity : Activity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private const val SERVICE_ID = "com.plattysoft.nearby"
        private const val NICKNAME = "IoT Temp Sensor"
        private val STRATEGY = Strategy.P2P_STAR
    }

    private lateinit var temperatureSensor: Bmx280
    private lateinit var redLed: Gpio

    private lateinit var nearbyConnections: ConnectionsClient

    private var connectedEndpoint: String? = null
    private val handler = Handler()

    private val payloadCallback= object: PayloadCallback() {
        override fun onPayloadReceived(p0: String, p1: Payload) {
            // I have received something
            val jsonObject = p1.getAsJson()
            val redLedStatus = jsonObject.getBoolean("status")
            redLed.value = redLedStatus
        }

        override fun onPayloadTransferUpdate(p0: String, p1: PayloadTransferUpdate) {
        }
    }

    private val connectionLifecycleCallback= object : ConnectionLifecycleCallback (){
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.statusCode == STATUS_OK) {
                connectedEndpoint = endpointId
            }
        }
        override fun onDisconnected(endpointId: String) {
            if (endpointId.equals(connectedEndpoint)) {
                connectedEndpoint = null
            }
        }
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            nearbyConnections.acceptConnection(endpointId, payloadCallback)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nearbyConnections = Nearby.getConnectionsClient(this)

        startAdvertising()

        redLed = RainbowHat.openLedRed()

        temperatureSensor = RainbowHat.openSensor()
        temperatureSensor.setMode(Bmx280.MODE_NORMAL)
        temperatureSensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X

        handler.post(object: Runnable {
            override fun run() {
                val temp = temperatureSensor.readTemperature()
                Log.d(TAG, "Temperature: "+temp.toDouble())
                if (connectedEndpoint != null) {
                    sendTemperatureToNearby(temp)
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun sendTemperatureToNearby(temp: Float) {
        val json = JSONObject()
        json.put("temperature", temp)
        val payload = Payload.fromBytes(json.toString().toByteArray())
        nearbyConnections.sendPayload(connectedEndpoint!!, payload)
    }

    private fun startAdvertising() {
        nearbyConnections.startAdvertising(
                NICKNAME,
                SERVICE_ID,
                connectionLifecycleCallback,
                AdvertisingOptions.Builder().setStrategy(STRATEGY).build())
                .addOnSuccessListener {
                    Log.e(TAG, "Advertising...")
                }
                .addOnFailureListener {
                    Log.e(TAG, "onFailure: "+it.localizedMessage)
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        temperatureSensor.close()
        redLed.close()
        nearbyConnections.stopAdvertising()
    }
}

private fun Payload.getAsJson(): JSONObject {
    val string = String(this.asBytes()!!)
    return JSONObject(string)
}
