package com.piconemarc.core.data.category

import com.piconemarc.core.domain.entityDTO.CategoryDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDaoImpl: CategoryDaoImpl) {

    fun getAllCategories() : Flow<List<CategoryDTO>> {
        return categoryDaoImpl.getAllCategories()
    }

    suspend fun addNewCategory(categoryDTO: CategoryDTO){
        categoryDaoImpl.addNewCategory(categoryDTO)
    }
}