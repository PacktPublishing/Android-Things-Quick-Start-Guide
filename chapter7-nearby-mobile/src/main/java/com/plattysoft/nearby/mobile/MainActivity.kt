package com.plattysoft.nearby.mobile

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes.STATUS_OK
import org.json.JSONObject

class MainActivity : Activity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private const val SERVICE_ID = "com.plattysoft.nearby"
        private const val NICKNAME = "IoT Phone" // This could be the device ID
        private val STRATEGY = Strategy.P2P_STAR
    }

    lateinit var nearbyConnection : ConnectionsClient
    private var connectedEndpoint: String? = null

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            connectToEndpoint(endpointId)
        }
        override fun onEndpointLost(endpointId: String) {
            // A previously discovered endpoint has gone away.
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback(){
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
            nearbyConnection.acceptConnection(endpointId, payloadCallback);
        }
    }

    private val payloadCallback = object: PayloadCallback() {
        override fun onPayloadReceived(p0: String, payload: Payload) {
            // Info about the temperature
            val json = payload.getAsJson()
            runOnUiThread {
                val value = json.getDouble("temperature").toString()
                findViewById<TextView>(R.id.temperatureValue).text = value
            }
        }

        override fun onPayloadTransferUpdate(p0: String, p1: PayloadTransferUpdate) {
            // Nothing to do here
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nearbyConnection = Nearby.getConnectionsClient(this)
        startDiscovery()
        findViewById<Switch>(R.id.redLedSwitch).setOnCheckedChangeListener { compoundButton: CompoundButton, status: Boolean ->
            if (connectedEndpoint != null) {
                val jsonObject = JSONObject()
                jsonObject.put("status", status)
                nearbyConnection.sendPayload(connectedEndpoint!!, Payload.fromBytes(jsonObject.toString().toByteArray()))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nearbyConnection.stopDiscovery()
    }

    private fun connectToEndpoint(endpointId: String) {
        nearbyConnection.requestConnection(
                NICKNAME,
                endpointId,
                connectionLifecycleCallback)
    }

    private fun startDiscovery() {
        nearbyConnection.startDiscovery(
                SERVICE_ID,
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder().setStrategy(STRATEGY).build())
                .addOnFailureListener {
                    Log.e(TAG, "onFailure: "+it.localizedMessage)
                }
                .addOnSuccessListener {
                    Log.e(TAG, "Discovering...")
                }
    }

    private fun Payload.getAsJson(): JSONObject {
        val string = String(this.asBytes()!!)
        return JSONObject(string)
    }
}
