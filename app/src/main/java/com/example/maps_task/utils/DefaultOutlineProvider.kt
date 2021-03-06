package com.example.maps_task.utils

import android.annotation.TargetApi
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class DefaultOutlineProvider(private val radius: Float) : ViewOutlineProvider() {

    private val rect: Rect = Rect()

    override fun getOutline(view: View?, outline: Outline?) {
        view?.background?.copyBounds(rect)
        outline?.alpha = 0.6f
        outline?.setRoundRect(rect, radius)
    }
}