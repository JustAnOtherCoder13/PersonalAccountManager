package com.piconemarc.core.domain.interactor.category

import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.entity.CategoryUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllCategoriesInteractor @Inject constructor(private val categoryRepository: CategoryRepository) :
    Constants.Interactor {

    fun getAllCategories(): Flow<List<CategoryUiModel>> = categoryRepository.getAllCategories().map {
            allCategoriesDTO -> allCategoriesDTO.map { it.toUiModel() }
    }
}