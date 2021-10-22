package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationModel
import javax.inject.Inject

class AddNewOperationInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun addNewOperation(operationModel: OperationModel) {
        operationRepository.addNewOperation(
            OperationDTO(
                name = operationModel.name,
                amount = operationModel.amount,
                endDateMonth = operationModel.endDate.month,
                endDateYear = operationModel.endDate.year,
                isRecurrent = operationModel.isRecurrent,
                accountId = operationModel.accountId,
                categoryId = operationModel.categoryId,
                emitDate = operationModel.emitDate
            )
        )
    }

}