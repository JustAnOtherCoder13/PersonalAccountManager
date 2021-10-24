package com.piconemarc.core.data.payment

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import javax.inject.Inject

class PaymentDaoImpl@Inject constructor(private val pamDatabase: PAMDatabase) : PaymentDao {

    private val paymentDao = pamDatabase.paymentDao()

    override suspend fun addNewPayment(paymentDTO: PaymentDTO) {
        paymentDao.addNewPayment(paymentDTO)
    }

    override suspend fun deletePayment(paymentDTO: PaymentDTO) {
        paymentDao.deletePayment(paymentDTO)
    }

    override suspend fun getAllPaymentForAccountId(accountId: Long): List<PaymentDTO> {
        return paymentDao.getAllPaymentForAccountId(accountId)
    }
}