package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import javax.inject.Inject

class UpdateOperationTransferIdInteractor @Inject constructor( private val operationRepository: OperationRepository) {

    suspend fun updateOperationTransferId(transferId: Long,vararg operationId: Long) {
        operationId.forEach {
            operationRepository.updateOperationTransferId(transferId, it)
        }
    }
}