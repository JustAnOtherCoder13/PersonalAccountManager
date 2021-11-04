package com.piconemarc.core.data.payment

import com.piconemarc.core.domain.entityDTO.PaymentDTO
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val paymentDao: PaymentDaoImpl) {

    suspend fun deletePayment(paymentDTO: PaymentDTO) {
        paymentDao.deletePayment(paymentDTO)
    }

    suspend fun getAllPaymentForAccountId(accountId: Long): List<PaymentDTO> {
        return paymentDao.getAllPaymentForAccountId(accountId)
    }

    suspend fun getPaymentForId(id: Long): PaymentDTO {
        return paymentDao.getPaymentForId(id)
    }

}