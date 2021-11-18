package com.piconemarc.core.data.payment

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import javax.inject.Inject

class PaymentDaoImpl@Inject constructor(pamDatabase: PAMDatabase) : PaymentDao {

    private val paymentDao = pamDatabase.paymentDao()

    override suspend fun deletePayment(paymentDTO: PaymentDTO) {
        paymentDao.deletePayment(paymentDTO)
    }

    override suspend fun deleteObsoletePayments(obsoletePayments: List<PaymentDTO>) {
        paymentDao.deleteObsoletePayments(obsoletePayments)
    }

    override suspend fun getAllPaymentForAccountId(accountId: Long): List<PaymentDTO> {
        return paymentDao.getAllPaymentForAccountId(accountId)
    }

    override suspend fun getPaymentForId(id: Long): PaymentDTO {
        return paymentDao.getPaymentForId(id)
    }

    override suspend fun getAllPayments(): List<PaymentDTO> {
        return paymentDao.getAllPayments()
    }
}