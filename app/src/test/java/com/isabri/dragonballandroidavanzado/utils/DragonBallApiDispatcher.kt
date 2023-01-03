package com.isabri.dragonballandroidavanzado.utils

import com.isabri.dragonballandroidavanzado.defaultData.Default
import com.isabri.dragonballandroidavanzado.utils.getJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Utf8
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

internal class DragonBallApiDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/api/heros/all" -> {
                if (request.body.toString().contains("\"\"")) {
                    return MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("json/heros.json"))
                } else {
                    return MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("json/roshi.json"))
                }
            }
            "/api/auth/login" -> {
                 return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                     .setHeader("Content-type", "text/plain")
                    .setBody("TOKEN")
            }
            "/api/heros/locations" -> {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/roshiLocations.json"))
            }
            "/api/data/herolike" -> {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setHeader("Content-type", "text/plain")
                    .setBody("FAVORITE")
            }
            else -> MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
        }
    }

}
