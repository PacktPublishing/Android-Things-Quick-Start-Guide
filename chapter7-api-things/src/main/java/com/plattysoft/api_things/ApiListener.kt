package com.plattysoft.api_things

import org.json.JSONObject

interface ApiListener {

    fun onGetTemperature(): JSONObject

    fun onPostLed(request: JSONObject)
}
