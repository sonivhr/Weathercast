package com.weathercast.util

import org.junit.Assert.assertEquals
import org.junit.Test

class TestUtil {
    @Test
    fun `verify scenario when date time does not come with predicted format`() {
        val someDate = "01 - 01 - 2021"
        assertEquals(someDate.convertToReadableDateTime(), someDate)
    }

    @Test
    fun `verify scenario when date time comes with predicted format`() {
        val someDate = "2021-09-13 15:00:00"
        assertEquals(someDate.convertToReadableDateTime(), "13 Sep, 03:00 PM")
    }
}
