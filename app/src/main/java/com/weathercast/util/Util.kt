package com.weathercast.util

import java.text.ParseException
import java.text.SimpleDateFormat

private const val API_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
private const val APPLICATION_DATE_TIME_FORMAT = "d MMM, hh:mm a"

fun String.convertToReadableDateTime(): String {
    val apiDateFormat = SimpleDateFormat(API_DATE_TIME_FORMAT)
    val applicationDateFormat = SimpleDateFormat(APPLICATION_DATE_TIME_FORMAT)
    return try {
        applicationDateFormat.format(apiDateFormat.parse(this))
    } catch (parseException: ParseException) {
        this
    }
}