package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetAllOperationsInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    fun getAllOperations(allCategories: List<CategoryModel>): Flow<List<OperationModel>> =
        operationRepository.getAllOperations().map {
            mapAllOperationsDtoToOperationsModel(it, allCategories)
        }

    private fun mapAllOperationsDtoToOperationsModel(
        operationsDtoList: List<OperationDTO>,
        allCategories: List<CategoryModel>
    ): List<OperationModel> {
        return operationsDtoList.map {OperationModel(
            id = it.id,
            name = it.name,
            amount_ = it.amount,
            categoryId = it.categoryId,
            emitDate = it.emitDate?: Date()
        )  }
    }
}
