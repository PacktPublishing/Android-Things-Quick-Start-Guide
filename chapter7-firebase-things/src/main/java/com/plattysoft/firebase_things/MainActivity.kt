package com.plattysoft.firebase_things

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MainActivity : Activity() {

    lateinit var redLed: Gpio
    lateinit var temperatureSensor: Bmx280

    lateinit var firebaseReference: DatabaseReference

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val database = FirebaseDatabase.getInstance()
        firebaseReference = database.getReference()

        redLed = RainbowHat.openLedRed()

        temperatureSensor = RainbowHat.openSensor()
        temperatureSensor.setMode(Bmx280.MODE_NORMAL)
        temperatureSensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X

        configureTemperatureReading()
        configureLedSwitch()
    }

    override fun onDestroy() {
        super.onDestroy()
        redLed.close()
        temperatureSensor.close()
    }

    private fun configureTemperatureReading() {
        handler.post(object : Runnable {
            override fun run() {
                val temperature = temperatureSensor.readTemperature()
                firebaseReference.child("temperature").setValue(temperature)
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun configureLedSwitch() {
        firebaseReference.child("redLED").addValueEventListener(
                object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        // Nothing to do here
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val redLedState = snapshot.getValue(Boolean::class.java)
                        if (redLedState != null) {
                            redLed.value = redLedState
                        }
                    }

                })
    }
}
