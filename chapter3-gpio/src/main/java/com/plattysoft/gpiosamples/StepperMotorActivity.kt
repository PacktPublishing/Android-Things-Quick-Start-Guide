package com.plattysoft.gpiosamples

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.plattysoft.uln2003.Direction
import com.plattysoft.uln2003.driver.ULN2003Resolution
import com.plattysoft.uln2003.listener.RotationListener
import com.plattysoft.uln2003.motor.ULN2003StepperMotor

/**
 * Created by Raul Portales on 19/05/18.
 */
class StepperMotorActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val in1Pin = "BCM4"
        val in2Pin = "BCM17"
        val in3Pin = "BCM27"
        val in4Pin = "BCM22"

        val stepper = ULN2003StepperMotor(in1Pin, in2Pin, in3Pin, in4Pin)

        //Perform a rotation and add rotation listener
        stepper.rotate(degrees = 180.0,
                direction = Direction.CLOCKWISE,
                resolutionId = ULN2003Resolution.HALF.id,
                rpm = 2.5,
                rotationListener = object : RotationListener {
                    override fun onStarted() {
                        Log.i(TAG, "rotation started")
                    }
                    override fun onFinishedSuccessfully() {
                        Log.i(TAG, "rotation finished")
                    }
                    override fun onFinishedWithError(degreesToRotate: Double, rotatedDegrees: Double, exception: Exception) {
                        Log.e(TAG, "error, degrees to rotate: {$degreesToRotate}  rotated degrees: {$rotatedDegrees}")
                    }
                })

        // Close the ULN2003StepperMotor when all moves are finished. Otherwise close() will terminate current and pending rotations.
        stepper.close()
    }

}