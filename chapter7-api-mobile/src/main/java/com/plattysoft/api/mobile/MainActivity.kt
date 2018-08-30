package com.plattysoft.api.mobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "http://192.168.42.38:8080/"
    }

    private val apiService: ThingsApi

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiService = retrofit.create(ThingsApi::class.java)

    }

    val handler = android.os.Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureLedSwitch()
        configureTemperatureReading()
    }

    private fun configureLedSwitch() {
        findViewById<Switch>(R.id.redLedSwitch).setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            sendPostLed(b)
        }
    }

    private fun sendPostLed(status: Boolean) {
        val ledValue = LedValue()
        ledValue.status = status
        apiService.setLedValue(ledValue).enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e("MainActivity", t.toString())
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                // All good
            }
        })
    }

    private fun configureTemperatureReading() {
        handler.post(object : Runnable {
            override fun run() {
                updateTemperature()
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateTemperature() {
        val result = apiService.getTemperature().enqueue (object: Callback<TemperatureResponse> {
            override fun onFailure(call: Call<TemperatureResponse>?, t: Throwable?) {
                Log.e("MainActivity", t.toString())
            }

            override fun onResponse(call: Call<TemperatureResponse>?, response: Response<TemperatureResponse>?) {
                runOnUiThread {
                    findViewById<TextView>(R.id.temperatureValue).text = response!!.body()!!.temperature.toString()
                }
            }
        })
    }
}
