package com.mtzdev.fakerdataclass.generators

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.graphics.toColor

internal object ResourceGenerator {

    private val STRING_TEST_LABEL_1 = 1002

    private val COLOR_TEST = Color.rgb(0, 0, 0)

    private val DRAWABLE_TEST = object : Drawable() {
        override fun draw(canvas: Canvas) {}
        override fun setAlpha(alpha: Int) {}
        override fun setColorFilter(colorFilter: ColorFilter?) {}
        override fun getOpacity(): Int = PixelFormat.OPAQUE
    }

    enum class ResourceType {
        STRING,
        DRAWABLE,
        COLOR
    }

    /**
     * Genera un recurso aleatorio.
     *
     * @param resourceType Tipo del recurso
     * @return Un recurso generado
     */
    fun generateResource(resourceType: ResourceType): Any {
        return when (resourceType) {
            ResourceType.STRING ->
                STRING_TEST_LABEL_1
            ResourceType.DRAWABLE ->
                DRAWABLE_TEST
            ResourceType.COLOR ->
                COLOR_TEST.toColor()
        }
    }
}