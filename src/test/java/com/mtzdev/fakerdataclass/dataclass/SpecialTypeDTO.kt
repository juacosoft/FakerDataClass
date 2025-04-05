package com.mtzdev.fakerdataclass.dataclass

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SpecialTypeDTO(
    @DrawableRes
    val userImage: Int,
    val userName: Color,
    val userPoster:Drawable,
    @StringRes
    val userNameRes: Int
)
