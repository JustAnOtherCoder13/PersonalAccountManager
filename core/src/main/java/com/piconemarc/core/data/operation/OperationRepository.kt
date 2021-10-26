package com.piconemarc.core.data.operation

import com.piconemarc.core.domain.entityDTO.OperationDTO
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


    fun getAllOperations(): Flow<List<OperationDTO>> {
        return operationDaoImpl.getAllOperations()
    }

    fun getOperationsForAccountId(accountId: Long): Flow<List<OperationDTO>> {
        return operationDaoImpl.getAllOperationsForAccountId(accountId)
    }

    suspend fun addNewOperation(operationDTO: OperationDTO): Long {
        return operationDaoImpl.addNewOperation(operationDTO)
    }

    suspend fun deleteOperation(operationDTO: OperationDTO) {
        operationDaoImpl.deleteOperation(operationDTO)
    }

    suspend fun getOperationForId(operationId: Long): OperationDTO {
        return operationDaoImpl.getOperationForId(operationId)
    }

    suspend fun updateOperationPaymentId(paymentId: Long, operationId: Long) {
        operationDaoImpl.updateOperationPaymentId(paymentId, operationId)
    }

    suspend fun updateOperationTransferId(transferId: Long, operationId: Long) {
        operationDaoImpl.updateOperationTransferId(transferId, operationId)
    }
}