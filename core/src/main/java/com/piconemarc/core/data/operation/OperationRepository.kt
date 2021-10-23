package com.piconemarc.core.data.operation

import com.piconemarc.core.domain.entityDTO.OperationDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OperationRepository @Inject constructor(private val operationDaoImpl: OperationDaoImpl) {

    fun getAllOperations() : Flow<List<OperationDTO>> {
        return operationDaoImpl.getAllOperations()
    }

    fun getOperationsForAccountId(accountId : Long) : Flow<List<OperationDTO>>{
        return operationDaoImpl.getAllOperationsForAccountId(accountId)
    }

    suspend fun addNewOperation(operationDTO: OperationDTO){
        operationDaoImpl.addNewOperation(operationDTO)
    }

    suspend fun deleteOperation(operationDTO: OperationDTO){
        operationDaoImpl.deleteOperation(operationDTO)
    }

    suspend fun getOperationForAccountIdWithOperationId(operationId: Long, accountId: Long) :OperationDTO {
        return operationDaoImpl.getOperationForAccountIdWithOperationId(operationId,accountId)
    }
}