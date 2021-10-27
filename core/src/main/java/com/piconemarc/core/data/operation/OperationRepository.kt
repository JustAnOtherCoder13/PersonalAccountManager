package com.piconemarc.core.data.operation

import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.TransferDTO
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class OperationRepository @Inject constructor(private val operationDaoImpl: OperationDaoImpl) {

    suspend fun addOperation(operationDTO: OperationDTO) {
        operationDaoImpl.addOperation(operationDTO)
    }

    suspend fun addPaymentOperation(operation: OperationDTO, endDate: Date?) {
        operationDaoImpl.addPaymentOperation(operation, endDate)
    }

    suspend fun addTransferOperation(operation: OperationDTO, beneficiaryAccountId: Long) {
        operationDaoImpl.addTransferOperation(operation, beneficiaryAccountId)
    }

    suspend fun deleteOperation(operationDTO: OperationDTO) {
        operationDaoImpl.deleteOperation(operationDTO)
    }

    suspend fun deletePayment(operationDTO: OperationDTO) {
        operationDaoImpl.deletePayment(operationDTO)
    }

    suspend fun deleteTransfer(operationDTO: OperationDTO,transfer : TransferDTO) {
        operationDaoImpl.deleteTransfer(operationDTO,transfer)
    }

    fun getOperationsForAccountIdFlow(accountId: Long): Flow<List<OperationDTO>> {
        return operationDaoImpl.getAllOperationsForAccountId(accountId)
    }

    suspend fun getOperationForId(operationId: Long): OperationDTO {
        return operationDaoImpl.getOperationForId(operationId)
    }
}