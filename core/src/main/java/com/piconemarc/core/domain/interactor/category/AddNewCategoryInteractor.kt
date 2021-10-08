package com.piconemarc.core.domain.interactor.category

import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.model.entity.CategoryModel
import javax.inject.Inject

class AddNewCategoryInteractor @Inject constructor(private val categoryRepository: CategoryRepository) {

    suspend fun addNewCategory(categoryModel: CategoryModel) {
        categoryRepository.addNewCategory(
            CategoryDTO(name = categoryModel.name, color = categoryModel.color)
        )
    }
}