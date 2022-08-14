package com.weathercast.mockapi

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockApiResponseDispatcher(
    private val mockResponseMap: Map<String, MockResponse>
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return request.path?.let {
            if (mockResponseMap.containsKey(it)) {
                mockResponseMap.getValue(it)
            } else {
                MockResponse().setResponseCode(404)
            }
        } ?: MockResponse().setResponseCode(404)
    }
}