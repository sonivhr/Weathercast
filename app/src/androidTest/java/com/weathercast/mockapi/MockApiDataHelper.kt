package com.weathercast.mockapi

import okhttp3.mockwebserver.MockResponse

object MockApiDataHelper {

    fun mockSuccessResponse(mockDataFilePath: String): MockResponse {
        val successResponseString = getFileContents(mockDataFilePath)
        return MockResponse()

            .setResponseCode(200)
            .setBody(successResponseString)
    }

    fun mockErrorResponse(errorCode: Int = 404): MockResponse {
        return MockResponse()
            .setResponseCode(errorCode)
    }

    private fun getFileContents(filePath: String): String {
        return Thread.currentThread()
            .contextClassLoader
            .getResourceAsStream(filePath)
            .reader()
            .readText()
    }
}