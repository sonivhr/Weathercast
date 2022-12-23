package com.weathercast.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class DispatcherProviderImp : DispatcherProvider {

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    override val main: CoroutineDispatcher = testCoroutineDispatcher

    override val io: CoroutineDispatcher = testCoroutineDispatcher

    override val default: CoroutineDispatcher = testCoroutineDispatcher

    override val unconfined: CoroutineDispatcher = testCoroutineDispatcher
}
