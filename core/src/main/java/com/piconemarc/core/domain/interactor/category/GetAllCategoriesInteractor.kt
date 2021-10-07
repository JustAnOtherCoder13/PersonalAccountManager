package com.piconemarc.core.domain.interactor.category

import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.model.entity.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllCategoriesInteractor(private val categoryRepository: CategoryRepository) :
    BaseCategoryInteractor(
        categoryRepository
    ) {

    fun getAllCategories(): Flow<List<CategoryModel>> = categoryRepository.getAllCategories().map {
        mapAllCategoriesDTOtoCategoryModel(it)
    }

    fun getAllCategoriesToStringList(): Flow<List<String>> =
        categoryRepository.getAllCategories().map {
            mapAllCategoriesToStringList(it)
        }

    private fun mapAllCategoriesDTOtoCategoryModel(categoriesDtoList: List<CategoryDTO>): List<CategoryModel> {
        val categoriesModelList = mutableListOf<CategoryModel>()
        categoriesDtoList.forEachIndexed { index, categoryDTO ->
            categoriesModelList.add(
                index = index,
                element = CategoryModel(
                    id = categoryDTO.id,
                    name = categoryDTO.name,
                    color = categoryDTO.color
                )
            )
        }
        return categoriesModelList
    }

    private fun mapAllCategoriesToStringList(categoriesDtoList: List<CategoryDTO>): List<String> {
        val categoriesToStringList = mutableListOf<String>()
        categoriesDtoList.forEach { categoryDTO ->
            categoriesToStringList.add(categoryDTO.name)
        }

        return categoriesToStringList
    }

}