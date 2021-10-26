package com.piconemarc.core.data.operation

import androidx.room.*
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.core.domain.utils.Constants.OPERATION_TABLE
import com.piconemarc.model.entity.TransferUiModel
import kotlinx.coroutines.flow.Flow
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Dao
interface OperationDao {

    @Transaction
    suspend fun addOperation(operationDTO: OperationDTO) : Long{
        val operationId = addNewOperation(operationDTO)
        val account = getAccountForId(operationDTO.accountId).toUiModel()
            .updateAccountBalance(operationDTO.toUiModel())
        updateAccountBalance(operationDTO.accountId, account.accountBalance)
        return operationId
    }

    @Transaction
    suspend fun addPaymentOperation(operation: OperationDTO, endDate: Date?){
        val operationId = addOperation(operation)
        val paymentId = addNewPayment(
            PaymentDTO(
                name = operation.name + "Payment",
                operationId = operationId,
                accountId = operation.accountId,
                endDate = endDate
            )
        )
        updateOperationPaymentId(paymentId, operationId)
    }

    @Transaction
    suspend fun addTransferOperation(operation: OperationDTO, beneficiaryAccountId : Long){
        val senderOperation = operation.copy(amount = operation.toUiModel().senderAmount)
        val beneficiaryOperation = operation.copy(accountId = beneficiaryAccountId, amount = operation.toUiModel().beneficiaryAmount)
        val senderOperationId = addOperation(senderOperation)
        val beneficiaryOperationId = addOperation(beneficiaryOperation)
        val transferId = addNewTransferOperation(
            TransferDTO(
                name = operation.name + "Transfer",
                senderOperationId = senderOperationId,
                beneficiaryOperationId = beneficiaryOperationId
            )
        )
        updateOperationTransferId(transferId,senderOperationId)
        updateOperationTransferId(transferId,beneficiaryOperationId)
    }

    @Insert
    suspend fun addNewTransferOperation(transferDTO: TransferDTO) : Long

    @Insert
    suspend fun addNewPayment(paymentDTO: PaymentDTO) : Long

    @Insert
    suspend fun addNewOperation(operationDTO: OperationDTO) : Long

    @Query("SELECT*FROM ${Constants.ACCOUNT_TABLE} WHERE ${Constants.ACCOUNT_TABLE}.id = :id")
    suspend fun getAccountForId(id:Long) : AccountDTO

    @Query("UPDATE ${Constants.ACCOUNT_TABLE} SET accountBalance = :accountBalance WHERE id = :accountId")
    suspend fun updateAccountBalance(accountId : Long, accountBalance : Double)




    @Query("SELECT*FROM $OPERATION_TABLE")
    fun getAllOperations() : Flow<List<OperationDTO>>

    @Query("SELECT*FROM $OPERATION_TABLE WHERE $OPERATION_TABLE.accountId = :accountId")
    fun getAllOperationsForAccountId(accountId : Long) : Flow<List<OperationDTO>>

    @Query("SELECT*FROM $OPERATION_TABLE WHERE $OPERATION_TABLE.id = :operationId")
    suspend fun getOperationForId(operationId : Long) : OperationDTO

    @Delete
    suspend fun deleteOperation (operationDTO: OperationDTO)

    @Query("UPDATE $OPERATION_TABLE SET paymentId = :paymentId WHERE id = :operationId" )
    suspend fun updateOperationPaymentId(paymentId : Long, operationId : Long)

    @Query("UPDATE $OPERATION_TABLE SET transferId = :transferId WHERE id  = :operationId" )
    suspend fun updateOperationTransferId(transferId : Long, operationId : Long)
}