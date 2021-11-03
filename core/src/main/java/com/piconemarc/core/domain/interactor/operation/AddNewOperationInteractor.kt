package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import java.util.*
import javax.inject.Inject

class AddNewOperationInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun addOperation(operationModel: OperationUiModel) {
        return operationRepository.addOperation(OperationDTO().fromUiModel(operationModel))
    }
    suspend fun addPaymentAndOperation(operation: OperationUiModel, endDate: Date?) {
        operationRepository.addPaymentAndOperation(OperationDTO().fromUiModel(operation), endDate)
    }

    suspend fun addNewPayment(payment: PaymentUiModel): Long {
        return operationRepository.addNewPayment(PaymentDTO().fromUiModel(payment))
    }

    suspend fun addTransferOperation(operation: OperationUiModel, beneficiaryAccountId: Long) {
        operationRepository.addTransferOperation(OperationDTO().fromUiModel(operation), beneficiaryAccountId)
    }

}