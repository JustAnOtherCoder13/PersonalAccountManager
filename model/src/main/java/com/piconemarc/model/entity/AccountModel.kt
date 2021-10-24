package com.piconemarc.model.entity

data class AccountModel (
    override val id : Long = 0,
    override val name : String = "",
    val accountBalance : Double = 0.0,
    val accountOverdraft : Double = 0.0
): BaseUiModel()
