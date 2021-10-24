package com.piconemarc.core.domain.interactor.category

import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.domain.Constants
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.model.entity.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllCategoriesInteractor @Inject constructor(private val categoryRepository: CategoryRepository) :
    Constants.Interactor {

    fun getAllCategories(): Flow<List<CategoryModel>> = categoryRepository.getAllCategories().map {
        mapAllCategoriesDTOtoCategoryModel(it)
    }

    private fun mapAllCategoriesDTOtoCategoryModel(categoriesDtoList: List<CategoryDTO>): List<CategoryModel> {
        return categoriesDtoList.map { CategoryModel(id = it.id, name = it.name, color = it.color) }
    }
}