package com.piconemarc.core.domain.interactor.category

import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.entity.CategoryUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetAllCategoriesInteractor @Inject constructor(private val categoryRepository: CategoryRepository) :
    Constants.Interactor {

    suspend fun getAllCategoriesAsFlow(scope: CoroutineScope): StateFlow<List<CategoryUiModel>> {
        return categoryRepository.getAllCategories()
            .map { allCategoriesDTO -> allCategoriesDTO.map { it.toUiModel() }
            }.stateIn(scope)
    }
}