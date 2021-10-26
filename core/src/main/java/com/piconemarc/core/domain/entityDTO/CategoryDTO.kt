package com.piconemarc.core.domain.entityDTO

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.utils.Constants.CATEGORY_TABLE
import com.piconemarc.model.entity.CategoryUiModel

@Entity(tableName = CATEGORY_TABLE)
data class CategoryDTO (
    @PrimaryKey(autoGenerate = true)
    override var id : Long = 0,
    override val name : String = "",
    val color : Int = Color.TRANSPARENT
) : DTO<CategoryUiModel,CategoryDTO> {

    override fun fromUiModel(model: CategoryUiModel): CategoryDTO {
        return this.copy(
            id = model.id,
            name = model.name,
            color = model.color
        )
    }

    override fun toUiModel(): CategoryUiModel {
        return CategoryUiModel(id, name, color)
    }
}