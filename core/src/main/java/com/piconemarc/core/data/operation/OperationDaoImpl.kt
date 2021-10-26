package com.piconemarc.core.data.operation

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.core.domain.entityDTO.TransferDTO
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class OperationDaoImpl @Inject constructor(pamDatabase: PAMDatabase) : OperationDao {

    private val operationDao: OperationDao = pamDatabase.operationDao()

    override suspend fun addOperation(operationDTO: OperationDTO): Long {
        return operationDao.addOperation(operationDTO)
    }

    override suspend fun addPaymentOperation(operation: OperationDTO, endDate: Date?) {
        super.addPaymentOperation(operation, endDate)
    }

    override suspend fun addTransferOperation(operation: OperationDTO, beneficiaryAccountId: Long) {
        super.addTransferOperation(operation, beneficiaryAccountId)
    }

    override suspend fun deleteOperation_(operationDTO: OperationDTO) {
        super.deleteOperation_(operationDTO)
    }

    override suspend fun deletePayment(operationDTO: OperationDTO) {
        super.deletePayment(operationDTO)
    }

    override suspend fun deleteTransfer(operationDTO: OperationDTO,transfer : TransferDTO) {
        super.deleteTransfer(operationDTO,transfer)
    }


    override suspend fun deletePayment(paymentDTO: PaymentDTO) {
        operationDao.deletePayment(paymentDTO)
    }

    override suspend fun deleteTransferOperation(transferDTO: TransferDTO) {
        operationDao.deleteTransferOperation(transferDTO)
    }

    override suspend fun getTransferOperationForId(transferId: Long): TransferDTO {
        return getTransferOperationForId(transferId)
    }

    override suspend fun getPaymentForId(id: Long): PaymentDTO {
        return operationDao.getPaymentForId(id)
    }

    override suspend fun addNewTransferOperation(transferDTO: TransferDTO): Long {
        return operationDao.addNewTransferOperation(transferDTO)
    }

    override suspend fun addNewPayment(paymentDTO: PaymentDTO): Long {
        return operationDao.addNewPayment(paymentDTO)
    }

    override suspend fun getAccountForId(id: Long): AccountDTO {
        return operationDao.getAccountForId(id)
    }

    override suspend fun updateAccountBalance(accountId: Long, accountBalance: Double) {
        operationDao.updateAccountBalance(accountId, accountBalance)
    }

    override fun getAllOperations(): Flow<List<OperationDTO>> {
        return operationDao.getAllOperations()
    }

    override fun getAllOperationsForAccountId(accountId: Long): Flow<List<OperationDTO>> {
        return operationDao.getAllOperationsForAccountId(accountId)
    }

    override suspend fun getOperationForId(operationId: Long): OperationDTO {
        return operationDao.getOperationForId(operationId)
    }

    override suspend fun addNewOperation(operationDTO: OperationDTO): Long {
        return operationDao.addNewOperation(operationDTO)
    }

    override suspend fun deleteOperation(operationDTO: OperationDTO) {
        operationDao.deleteOperation(operationDTO)
    }

    override suspend fun updateOperationPaymentId(paymentId: Long, operationId: Long) {
        operationDao.updateOperationPaymentId(paymentId, operationId)
    }

    override suspend fun updateOperationTransferId(transferId: Long, operationId: Long) {
        operationDao.updateOperationTransferId(transferId, operationId)
    }
}