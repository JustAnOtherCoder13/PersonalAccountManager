package com.piconemarc.model.entity

import android.graphics.Color

data class CategoryUiModel(
    override val id: Long = 1,
    override val name: String = "None",
    val color: Int = Color.TRANSPARENT
): BaseUiModel()

