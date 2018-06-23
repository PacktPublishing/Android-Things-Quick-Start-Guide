package com.plattysoft.api_things

import android.util.Log
import fi.iki.elonen.NanoHTTPD
import org.json.JSONObject
import java.io.IOException

class ApiServer(private val mListener: ApiListener) : NanoHTTPD(8080) {

    init {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession): Response {
        val path = session.uri
        val method = session.method

        if (method === Method.GET && path == "/temperature") {
            val response = mListener.onGetTemperature()
            return newFixedLengthResponse(response.toString())
        }
        else if (method === Method.POST && path == "/led") {
            val request = getBodyAsJson(session)
            mListener.onPostLed(request)
            return newFixedLengthResponse("")
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404, file not found.")
    }

    private fun getBodyAsJson(session: NanoHTTPD.IHTTPSession): JSONObject {
        val files = HashMap<String, String>()
        session.parseBody(files)
        var content = files["postData"]
        return JSONObject(content)
    }
}
