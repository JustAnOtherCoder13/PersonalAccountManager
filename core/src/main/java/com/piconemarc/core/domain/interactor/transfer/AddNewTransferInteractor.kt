package com.piconemarc.core.domain.interactor.transfer

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import javax.inject.Inject

class AddNewTransferInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun addTransferOperation(operation: OperationUiModel, beneficiaryAccountId: Long) {
        operationRepository.addTransferOperation(OperationDTO().fromUiModel(operation), beneficiaryAccountId)
    }
}