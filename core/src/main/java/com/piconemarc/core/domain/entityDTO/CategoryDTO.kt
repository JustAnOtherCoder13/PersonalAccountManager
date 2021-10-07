package com.piconemarc.core.domain.entityDTO

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.CATEGORY_TABLE

@Entity(tableName = CATEGORY_TABLE)
data class CategoryDTO (
    @PrimaryKey
    var id : Long = 0,
    val name : String = "",
    val color : Int = Color.TRANSPARENT
)