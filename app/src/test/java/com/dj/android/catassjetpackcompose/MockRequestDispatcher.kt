package com.dj.android.catassjetpackcompose

import com.google.common.io.Resources
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest
import java.io.File
import java.net.HttpURLConnection

class MockRequestDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/cats?tag=cute" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("catsresponse.json"))
            }

            else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
        }
    }

    private fun getJson(path: String): String {
        val uri = Resources.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

}