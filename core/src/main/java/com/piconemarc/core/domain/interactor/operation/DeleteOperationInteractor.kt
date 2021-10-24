package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationModel
import javax.inject.Inject

class DeleteOperationInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun deleteOperation(operationModel: OperationModel) {
        operationRepository.deleteOperation(
            OperationDTO(
                id = operationModel.id,
                name = operationModel.name,
                amount = operationModel.updatedAmount,
                accountId = operationModel.accountId,
                categoryId = operationModel.categoryId,
                emitDate = operationModel.emitDate
            )
        )
    }
}