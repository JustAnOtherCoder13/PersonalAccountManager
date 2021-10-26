package com.piconemarc.core.data.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.core.domain.utils.Constants.CATEGORY_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT*FROM $CATEGORY_TABLE")
    fun getAllCategories(): Flow<List<CategoryDTO>>

    @Insert
    suspend fun addNewCategory(categoryDTO: CategoryDTO)
}