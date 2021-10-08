package com.piconemarc.core.data

import com.piconemarc.core.domain.Constants.TODAY
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO
import java.util.*

object Generator {

private val ACCOUNTS : List<AccountDTO> =  listOf(
    AccountDTO(id = 1,name = "myName1"),
    AccountDTO(id = 2,name = "myName2"),
    AccountDTO(id = 3,name = "myName3"),
)
    fun generateAccounts() : MutableList<AccountDTO>{
        return ACCOUNTS.toMutableList()
    }

private val OPERATIONS : List<OperationDTO> = listOf(
   OperationDTO(id = 1,name = "first", amount = 10.00, emitDate = TODAY),
   OperationDTO(id = 2,name = "second",amount = 50.00, emitDate = TODAY),
   OperationDTO(id = 3,name = "third",amount = -40.00, emitDate = TODAY)
)
    fun generateOperations() : MutableList<OperationDTO>{
        return OPERATIONS.toMutableList()
    }

private val CATEGORIES: List<CategoryDTO> = listOf(
    CategoryDTO(id = 1,name = "cat1"),
    CategoryDTO(id = 2,name = "cat2"),
    CategoryDTO(id = 3,name = "cat3")
)

    fun generateCategories() : MutableList<CategoryDTO>{
        return CATEGORIES.toMutableList()
    }

}