package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationModel

class AddNewOperationInteractor(private val operationRepository: OperationRepository) : BaseOperationInteractor(
    operationRepository
) {

    suspend fun addNewOperation(operationModel: OperationModel){
        operationRepository.addNewOperation(
            OperationDTO(
                name = operationModel.name,
                amount = operationModel.amount,
                endDateMonth = operationModel.endDate.month,
                endDateYear = operationModel.endDate.year,
                isRecurrent = operationModel.isRecurrent,
                accountId = operationModel.account.id,
                categoryId = operationModel.category.id,
                emitDate = operationModel.emitDate
            )
        )
    }

}