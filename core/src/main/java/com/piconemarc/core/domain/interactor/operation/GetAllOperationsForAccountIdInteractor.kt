package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.EndDate
import com.piconemarc.model.entity.OperationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetAllOperationsForAccountIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    fun getAllOperationsForAccountId(
        accountId: Long,
        allCategories: List<CategoryModel>
    ): Flow<List<OperationModel>> =
        operationRepository.getOperationsForAccountId(accountId).map {
            mapAllOperationsDtoForAccountIdToOperationModel(allCategories, it)
        }

    private fun mapAllOperationsDtoForAccountIdToOperationModel(
        allCategories: List<CategoryModel>,
        allOperationsDtoForAccountId: List<OperationDTO>
    ): List<OperationModel> {
        val operationsModelList = mutableListOf<OperationModel>()

        allOperationsDtoForAccountId.forEachIndexed { index, operationDTO ->
            operationsModelList.add(
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
                    emitDate = operationDTO.emitDate ?: Date()
                )
            )
        }
        return operationsModelList
    }
}