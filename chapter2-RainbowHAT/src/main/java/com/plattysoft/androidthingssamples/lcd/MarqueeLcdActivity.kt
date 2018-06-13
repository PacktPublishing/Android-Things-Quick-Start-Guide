package com.plattysoft.androidthingssamples.lcd

import android.app.Activity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by Raul Portales on 29/04/18.
 */
class MarqueeLcdActivity : Activity() {

    private lateinit var marquee: Marquee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        marquee = Marquee()
        marquee.displayText("SOMETHING LONG")
    }
}