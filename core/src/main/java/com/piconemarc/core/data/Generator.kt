package com.piconemarc.core.data

import com.piconemarc.core.domain.Constants.TODAY
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO

object Generator {

private val ACCOUNTS : List<AccountDTO> =  listOf(
    AccountDTO(id = 1,name = "Marc", accountBalance = -365.0,accountOverdraft = 500.0),
    AccountDTO(id = 2,name = "Charlotte", accountBalance = -496.0, accountOverdraft = 500.0),
    AccountDTO(id = 3,name = "Compte Commun", accountBalance = 1.3 , accountOverdraft = 100.0),
)
    fun generateAccounts() : MutableList<AccountDTO>{
        return ACCOUNTS.toMutableList()
    }

private val OPERATIONS : List<OperationDTO> = listOf(
   OperationDTO(id = 1,name = "Google payment", amount = 10.00, emitDate = TODAY,accountId = 1),
   OperationDTO(id = 2,name = "retrait",amount = -50.00, emitDate = TODAY, accountId = 1),
   OperationDTO(id = 3,name = "third",amount = -40.00, emitDate = TODAY, accountId = 1)
)
    fun generateOperations() : MutableList<OperationDTO>{
        return OPERATIONS.toMutableList()
    }

private val CATEGORIES: List<CategoryDTO> = listOf(
    CategoryDTO(),
    CategoryDTO(id = 2,name = "cat2"),
    CategoryDTO(id = 3,name = "cat3")
)

    fun generateCategories() : MutableList<CategoryDTO>{
        return CATEGORIES.toMutableList()
    }

}