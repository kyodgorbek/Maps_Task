package com.example.maps_task.widgets

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.example.maps_task.utils.DefaultOutlineProvider

class SoftCardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = DefaultOutlineProvider(radius)
        }
    }
}