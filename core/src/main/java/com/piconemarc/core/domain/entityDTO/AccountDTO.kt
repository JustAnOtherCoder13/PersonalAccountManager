package com.piconemarc.core.domain.entityDTO

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.ACCOUNT_TABLE
import com.piconemarc.model.entity.AccountModel

@Entity(tableName = ACCOUNT_TABLE)
data class AccountDTO(val accountModel: AccountModel) {
    @PrimaryKey(autoGenerate = true)
    val id : Long = accountModel.id
    val name : String = accountModel.name
    val accountBalance : Double = accountModel.accountBalance
}