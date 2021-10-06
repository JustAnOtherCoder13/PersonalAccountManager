package com.piconemarc.core.domain.entityDTO

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.CATEGORY_TABLE
import com.piconemarc.model.entity.CategoryModel

@Entity(tableName = CATEGORY_TABLE)
data class CategoryDTO (val categoryModel: CategoryModel)  {
    @PrimaryKey
    val id : Long = categoryModel.id
    val name : String = categoryModel.name
    val color : Int = categoryModel.color
}