package com.piconemarc.core.data.payment

import com.piconemarc.core.domain.entityDTO.PaymentDTO
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val paymentDao: PaymentDaoImpl) {

    suspend fun deletePayment(paymentDTO: PaymentDTO) {
        paymentDao.deletePayment(paymentDTO)
    }

    suspend fun deleteObsoletePayments(obsoletePayments: List<PaymentDTO>) {
        paymentDao.deleteObsoletePayments(obsoletePayments)
    }

    suspend fun getAllPaymentForAccountId(accountId: Long): List<PaymentDTO> {
        return paymentDao.getAllPaymentForAccountId(accountId)
    }

    suspend fun getPaymentForId(id: Long): PaymentDTO {
        return paymentDao.getPaymentForId(id)
    }

    suspend fun getAllPayments(): List<PaymentDTO> {
        return paymentDao.getAllPayments()
    }

}