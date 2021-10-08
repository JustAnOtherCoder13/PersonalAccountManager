package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.EndDate
import com.piconemarc.model.entity.OperationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

        val operationModelList = mutableListOf<OperationModel>()

        operationsDtoList.forEachIndexed { index, operationDTO ->
            operationModelList.add(
                index = index,
                element = OperationModel(
                    id = operationDTO.id,
                    name = operationDTO.name,
                    amount = operationDTO.amount,
                    endDate = EndDate(
                        month = operationDTO.endDateMonth,
                        year = operationDTO.endDateYear
                    ),
                    isRecurrent = operationDTO.isRecurrent,
                    category = allCategories.find { categoryModel -> categoryModel.id == operationDTO.categoryId }
                        ?: CategoryModel(),
                    emitDate = operationDTO.emitDate
                )
            )
        }
        return operationModelList
    }
}
