package com.piconemarc.core.data.payment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.piconemarc.core.domain.Constants.PAYMENT_TABLE
import com.piconemarc.core.domain.entityDTO.PaymentDTO

@Dao
interface PaymentDao {

    @Insert
    suspend fun addNewPayment(paymentDTO: PaymentDTO)

    @Delete
    suspend fun deletePayment(paymentDTO: PaymentDTO)

    @Query("SELECT*FROM $PAYMENT_TABLE WHERE $PAYMENT_TABLE.accountId = :accountId")
    suspend fun getAllPaymentForAccountId(accountId : Long): List<PaymentDTO>
}