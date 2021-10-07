package com.piconemarc.core.domain.entityDTO

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.ACCOUNT_TABLE

@Entity(tableName = ACCOUNT_TABLE)
data class AccountDTO(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    val name : String = "",
    val accountBalance : Double = 0.0,
)