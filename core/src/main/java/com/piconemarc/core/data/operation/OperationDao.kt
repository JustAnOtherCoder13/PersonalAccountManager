package com.piconemarc.core.data.operation

import android.util.Log
import androidx.room.*
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.core.domain.utils.Constants.OPERATION_TABLE
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
        Log.i("TAG", "addPaymentOperation: $operationId  $paymentId")
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

    @Transaction
    suspend fun deleteOperation_(operationDTO: OperationDTO){
        deleteOperation(operationDTO)
        val account = getAccountForId(operationDTO.accountId).toUiModel()
            .updateAccountBalance(operationDTO.toUiModel().deleteOperation())
        updateAccountBalance(operationDTO.accountId,account.accountBalance)
    }

    @Transaction
    suspend fun deletePayment(operationDTO: OperationDTO){
        val payment = getPaymentForId(operationDTO.paymentId!!)
        deletePayment(payment)
        deleteOperation_(operationDTO)
    }

    @Transaction
    suspend fun deleteTransfer(operationDTO: OperationDTO,transfer : TransferDTO){
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

    @Query("SELECT*FROM ${Constants.TRANSFER_TABLE} WHERE ${Constants.TRANSFER_TABLE}.id = :transferId")
    suspend fun getTransferOperationForId(transferId : Long) : TransferDTO

    @Delete
    suspend fun deletePayment(paymentDTO: PaymentDTO)

    @Query("SELECT*FROM ${Constants.PAYMENT_TABLE} WHERE ${Constants.PAYMENT_TABLE}.id = :id")
    suspend fun getPaymentForId(id : Long) : PaymentDTO

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