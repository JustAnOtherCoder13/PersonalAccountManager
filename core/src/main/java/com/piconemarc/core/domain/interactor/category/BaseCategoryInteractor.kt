package com.piconemarc.core.domain.interactor.category

import com.piconemarc.core.data.category.CategoryRepository
import javax.inject.Inject

open class BaseCategoryInteractor @Inject constructor(private val categoryRepository: CategoryRepository)