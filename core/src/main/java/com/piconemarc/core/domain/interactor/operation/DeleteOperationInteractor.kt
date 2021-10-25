package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import javax.inject.Inject

class DeleteOperationInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun deleteOperation(operationModel: OperationUiModel) {
        operationRepository.deleteOperation(
            OperationDTO().fromOperationModel(operationModel)
        )
    }
}