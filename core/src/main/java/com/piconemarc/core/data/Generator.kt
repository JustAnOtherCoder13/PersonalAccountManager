package com.piconemarc.core.data

import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.core.domain.utils.Constants.TODAY

object Generator {

    private val ACCOUNTS: List<AccountDTO> = listOf(
        AccountDTO(id = 1, name = "Marc", accountBalance = -365.0, accountOverdraft = 500.0),
        AccountDTO(id = 2, name = "Charlotte", accountBalance = -496.0, accountOverdraft = 500.0),
        AccountDTO(id = 3, name = "Compte Commun", accountBalance = 1.3, accountOverdraft = 100.0),
    )

    fun generateAccounts(): MutableList<AccountDTO> {
        return ACCOUNTS.toMutableList()
    }

    private val OPERATIONS: List<OperationDTO> = listOf(
        OperationDTO(id = 1, name = "Google payment", amount = 10.00, emitDate = TODAY, accountId = 1),
        OperationDTO(id = 2, name = "loyer", amount = -350.00, emitDate = TODAY, accountId = 1, paymentId = 2),
        OperationDTO(id = 3, name = "edf", amount = -40.00, emitDate = TODAY, accountId = 1,paymentId = 1)
    )

    fun generateOperations(): MutableList<OperationDTO> {
        return OPERATIONS.toMutableList()
    }

    private val PAYMENTS : List<PaymentDTO> = listOf(
        PaymentDTO(id = 1,name = "edfPayment",operationId = 3,accountId = 1),
        PaymentDTO(id = 2,name = "loyerPayment",operationId = 2,accountId = 1)
    )

    fun generatePayments(): MutableList<PaymentDTO>{
        return PAYMENTS.toMutableList()
    }

    private val CATEGORIES: List<CategoryDTO> = listOf(
        CategoryDTO(id = 1, name = "None"),
        CategoryDTO(id = 2, name = "cat2"),
        CategoryDTO(id = 3, name = "cat3")
    )

    fun generateCategories(): MutableList<CategoryDTO> {
        return CATEGORIES.toMutableList()
    }

}