package com.piconemarc.core.data.operation

import androidx.room.*
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.core.domain.utils.Constants.ACCOUNT_TABLE
import com.piconemarc.core.domain.utils.Constants.OPERATION_TABLE
import com.piconemarc.core.domain.utils.Constants.PAYMENT_TABLE
import com.piconemarc.core.domain.utils.Constants.TRANSFER_TABLE
import kotlinx.coroutines.flow.Flow
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
    suspend fun addPaymentAndOperation(operation: OperationDTO, endDate: Date?){
        val operationId = addOperation(operation)
        val paymentId = addNewPayment(
            PaymentDTO(
                name = operation.name,
                operationId = operationId,
                accountId = operation.accountId,
                endDate = endDate,
                operationAmount = operation.amount
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
                name = operation.name,
                senderOperationId = senderOperationId,
                beneficiaryOperationId = beneficiaryOperationId
            )
        )
        updateOperationTransferId(transferId,senderOperationId)
        updateOperationTransferId(transferId,beneficiaryOperationId)
    }

    @Transaction
    suspend fun deleteOperation(operationDTO: OperationDTO){
        this.deleteOperation_(operationDTO)
        val account = getAccountForId(operationDTO.accountId).toUiModel()
            .updateAccountBalance(operationDTO.toUiModel().deleteOperation())
        updateAccountBalance(operationDTO.accountId,account.accountBalance)
    }

    @Transaction
    suspend fun deleteOperationAndPayment(operationDTO: OperationDTO){
        val payment = getPaymentForId(operationDTO.paymentId!!)
        deletePayment(payment)
        this.deleteOperation(operationDTO)
    }

    @Transaction
    suspend fun deletePaymentAndRelatedOperation(paymentDTO: PaymentDTO){
        val operation = getOperationForId(paymentDTO.operationId!!)
        deleteOperation_(operation)
        deletePayment(paymentDTO)
    }

    @Transaction
    suspend fun deleteTransfer(operationDTO: OperationDTO, transfer : TransferDTO){
        //when try to catch transfer here, create multiple call and crash the app.
        //val transfer = getTransferOperationForId(operationDTO.transferId!!)
        val distantOperation = getOperationForId(
            if (operationDTO.id == transfer.senderOperationId) {
                transfer.beneficiaryOperationId
            } else {
                transfer.senderOperationId
            }
        )
        val selectedAccount = getAccountForId(operationDTO.accountId).toUiModel().updateAccountBalance(operationDTO.toUiModel().deleteOperation())
        val distantAccount = getAccountForId(distantOperation.accountId).toUiModel().updateAccountBalance(distantOperation.toUiModel().deleteOperation())
        deleteTransferOperation(transfer)
        updateAccountBalance(operationDTO.accountId, selectedAccount.accountBalance)
        updateAccountBalance(distantAccount.id,distantAccount.accountBalance)
    }


    @Delete
    suspend fun deleteTransferOperation(transferDTO: TransferDTO)

    @Query("SELECT*FROM $TRANSFER_TABLE WHERE $TRANSFER_TABLE.id = :transferId")
    suspend fun getTransferOperationForId(transferId : Long) : TransferDTO

    @Delete
    suspend fun deletePayment(paymentDTO: PaymentDTO)

    @Query("SELECT*FROM $PAYMENT_TABLE WHERE $PAYMENT_TABLE.id = :id")
    suspend fun getPaymentForId(id : Long) : PaymentDTO

    @Insert
    suspend fun addNewTransferOperation(transferDTO: TransferDTO) : Long

    @Insert
    suspend fun addNewPayment(paymentDTO: PaymentDTO) : Long

    @Insert
    suspend fun addNewOperation(operationDTO: OperationDTO) : Long

    @Query("SELECT*FROM $ACCOUNT_TABLE WHERE $ACCOUNT_TABLE.id = :id")
    suspend fun getAccountForId(id:Long) : AccountDTO

    @Query("UPDATE $ACCOUNT_TABLE SET accountBalance = :accountBalance WHERE id = :accountId")
    suspend fun updateAccountBalance(accountId : Long, accountBalance : Double)


    @Query("SELECT*FROM $OPERATION_TABLE")
    fun getAllOperations() : Flow<List<OperationDTO>>

    @Query("SELECT*FROM $OPERATION_TABLE WHERE $OPERATION_TABLE.accountId = :accountId")
    fun getAllOperationsForAccountId(accountId : Long) : Flow<List<OperationDTO>>

    @Query("SELECT*FROM $OPERATION_TABLE WHERE $OPERATION_TABLE.id = :operationId")
    suspend fun getOperationForId(operationId : Long) : OperationDTO

    @Delete
    suspend fun deleteOperation_ (operationDTO: OperationDTO)

    @Query("UPDATE $OPERATION_TABLE SET paymentId = :paymentId WHERE id = :operationId" )
    suspend fun updateOperationPaymentId(paymentId : Long, operationId : Long)

    @Query("UPDATE $OPERATION_TABLE SET transferId = :transferId WHERE id  = :operationId" )
    suspend fun updateOperationTransferId(transferId : Long, operationId : Long)
}