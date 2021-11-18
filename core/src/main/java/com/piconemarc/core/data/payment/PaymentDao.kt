package com.piconemarc.core.data.payment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.core.domain.utils.Constants.PAYMENT_TABLE

@Dao
interface PaymentDao {

    @Delete
    suspend fun deletePayment(paymentDTO: PaymentDTO)

    @Query("SELECT*FROM $PAYMENT_TABLE WHERE $PAYMENT_TABLE.accountId = :accountId")
    suspend fun getAllPaymentForAccountId(accountId : Long): List<PaymentDTO>

    @Query("SELECT*FROM $PAYMENT_TABLE WHERE $PAYMENT_TABLE.id = :id")
    suspend fun getPaymentForId(id : Long) : PaymentDTO

    @Query("SELECT*FROM $PAYMENT_TABLE")
    suspend fun getAllPayments() : List<PaymentDTO>
}