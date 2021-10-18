package com.piconemarc.model.entity

data class AccountModel(
    val id : Long = 0,
    val name : String = "",
    val accountBalance : Double = 0.0,
    val accountOverdraft : Double = 0.0
)
