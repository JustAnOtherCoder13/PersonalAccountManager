package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.CategoryUiModel
import com.piconemarc.model.entity.PaymentUiModel
import java.util.*
import javax.inject.Inject

class PassAllPaymentsForThisMonthInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun passAllPaymentForAccountOnThisMonth(allPaymentToPass: List<PaymentUiModel>) {
        allPaymentToPass.forEach {
            operationRepository.passPaymentForThisMonth(
                operation = OperationDTO(
                    accountId = it.accountId,
                    name = it.name,
                    amount = it.amount,
                    categoryId = CategoryUiModel().id,//todo replace with real category
                    emitDate = Calendar.getInstance().time,
                    paymentId = it.id
                ),
                paymentId = it.id
            )
        }
    }
}