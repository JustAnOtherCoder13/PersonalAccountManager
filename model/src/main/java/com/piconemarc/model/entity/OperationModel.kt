package com.piconemarc.model.entity

import java.util.*

data class OperationModel(
    val id : Long = 0,
    val name : String = "",
    val amount : Double = 0.0,
    val endDate : EndDate = EndDate(),
    val isRecurrent : Boolean = false,
    val account : AccountModel = AccountModel(),
    val categoryId : Long = 0,
    val emitDate : Date = Calendar.getInstance().time
)
data class EndDate(
    val month : String = "",
    val year : String = ""
)
