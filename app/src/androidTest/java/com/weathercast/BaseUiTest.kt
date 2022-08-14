package com.weathercast

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseUiTest {
    @get:Rule
    val activityRule = ActivityTestRule(
        MainActivity::class.java, true, false
    )

    private lateinit var mockWebServer: MockWebServer
    protected val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    protected fun setMockApiDispatcher(dispatcher: Dispatcher) {
        mockWebServer.dispatcher = dispatcher
    }

    protected fun enqueueResponse(mockResponse: MockResponse) {
        mockWebServer.enqueue(mockResponse)
    }
}