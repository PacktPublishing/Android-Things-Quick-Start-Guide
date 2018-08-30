package com.plattysoft.api.mobile

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Raul Portales on 13/06/18.
 */
interface ThingsApi {

    @GET("/temperature")
    fun getTemperature() : Call<TemperatureResponse>

    @POST("/led")
    fun setLedValue(@Body ledValue: LedValue): Call<Void>
}