package com.piconemarc.core.data.category

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDaoImpl @Inject constructor(pamDatabase: PAMDatabase) : CategoryDao {

    private val categoryDao : CategoryDao = pamDatabase.categoryDao()

    override fun getAllCategories(): Flow<List<CategoryDTO>> {
        return categoryDao.getAllCategories()
    }

    override suspend fun addNewCategory(categoryDTO: CategoryDTO){
        categoryDao.addNewCategory(categoryDTO)
    }
}