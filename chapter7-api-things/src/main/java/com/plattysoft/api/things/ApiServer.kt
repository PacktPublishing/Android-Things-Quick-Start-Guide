package com.plattysoft.api.things

import fi.iki.elonen.NanoHTTPD
import org.json.JSONObject

class ApiServer(private val listener: ApiListener) : NanoHTTPD(8080) {

    init {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession): Response {
        val path = session.uri
        val method = session.method

        if (method === Method.GET && path == "/temperature") {
            val response = listener.onGetTemperature()
            return newFixedLengthResponse(response.toString())
        }
        else if (method === Method.POST && path == "/led") {
            val request = getBodyAsJson(session)
            listener.onPostLed(request)
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
