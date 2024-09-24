package com.comst.ui.util

class ThrottleClickHandler(private val delayMillis: Long) {

    private var lastClickTime: Long = 0

    fun canHandleClick(): Boolean {
        val currentTime = System.currentTimeMillis()

        return if (currentTime - lastClickTime >= delayMillis) {
            lastClickTime = currentTime
            true
        } else {
            false
        }
    }
}