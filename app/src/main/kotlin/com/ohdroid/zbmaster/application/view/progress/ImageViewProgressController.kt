package com.ohdroid.zbmaster.application.view.progress

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * Created by ohdroid on 2016/4/21.
 */
open class ImageViewProgressController : Drawable() {
    override fun draw(canvas: Canvas?) {
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun onLevelChange(level: Int): Boolean {
        super.onLevelChange(level)
        return true
    }

}