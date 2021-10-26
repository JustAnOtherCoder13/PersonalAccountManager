package com.piconemarc.core.data.operation

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.OperationDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OperationDaoImpl @Inject constructor( pamDatabase: PAMDatabase) : OperationDao {

    private val operationDao : OperationDao = pamDatabase.operationDao()

    override fun getAllOperations(): Flow<List<OperationDTO>> {
        return operationDao.getAllOperations()
    }

    override fun getAllOperationsForAccountId(accountId: Long): Flow<List<OperationDTO>> {
       return operationDao.getAllOperationsForAccountId(accountId)
    }

    override suspend fun getOperationForId(operationId: Long) :OperationDTO {
        return operationDao.getOperationForId(operationId)
    }

    override suspend fun addNewOperation(operationDTO: OperationDTO): Long {
        return operationDao.addNewOperation(operationDTO)
    }

    override suspend fun deleteOperation(operationDTO: OperationDTO){
        operationDao.deleteOperation(operationDTO)
    }

    override suspend fun updateOperationPaymentId(paymentId: Long, operationId: Long) {
        operationDao.updateOperationPaymentId(paymentId, operationId)
    }

    override suspend fun updateOperationTransferId(transferId: Long, operationId: Long) {
        operationDao.updateOperationTransferId(transferId, operationId)
    }
}