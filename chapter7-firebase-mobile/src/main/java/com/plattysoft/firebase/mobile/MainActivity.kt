package com.plattysoft.firebase.mobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var firebaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        val database = FirebaseDatabase.getInstance()
        firebaseReference = database.getReference()

        redLedSwitch.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            firebaseReference.child("redLED").setValue(b)
        }

        firebaseReference.child("temperature").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                runOnUiThread {
                    val temperature = snapshot.getValue(Double::class.java)
                    temperatureValue.setText(temperature.toString())
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                // Nothing to do here
            }
        })
    }
}
