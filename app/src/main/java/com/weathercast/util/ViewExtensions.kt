package com.weathercast.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadOriginalImageWithGlide(iconName: String) {
    Glide.with(this.context)
        .load("https://openweathermap.org/img/wn/${iconName}.png")
        .into(this)
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}
