package com.piconemarc.model.entity

open class BaseUiModel(
    open val id : Long = 0,
    open val name : String = ""
)

open class BaseOperation(
    override val id: Long = 0,
    override val name: String = "",
    open val amount : Double = 0.0
) : BaseUiModel()