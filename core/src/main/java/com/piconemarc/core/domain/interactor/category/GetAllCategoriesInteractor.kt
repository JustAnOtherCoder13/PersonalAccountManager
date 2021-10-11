package com.piconemarc.core.domain.interactor.category

import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.DataUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllCategoriesInteractor @Inject constructor(private val categoryRepository: CategoryRepository) {

    fun getAllCategories(): Flow<List<CategoryModel>> = categoryRepository.getAllCategories().map {
        mapAllCategoriesDTOtoCategoryModel(it)
    }

    fun getAllCategoriesToDataUiModelList(): Flow<List<DataUiModel>> =
        categoryRepository.getAllCategories().map {
            mapAllCategoriesToStringList(it)
        }

    private fun mapAllCategoriesDTOtoCategoryModel(categoriesDtoList: List<CategoryDTO>): List<CategoryModel> {
        return categoriesDtoList.map { CategoryModel(id = it.id, name = it.name, color = it.color) }
    }

    private fun mapAllCategoriesToStringList(categoriesDtoList: List<CategoryDTO>): List<DataUiModel> {
        return categoriesDtoList.map { DataUiModel(it.name,it.id) }
    }

}