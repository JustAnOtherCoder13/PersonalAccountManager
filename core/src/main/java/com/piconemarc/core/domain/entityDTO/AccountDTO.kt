package com.piconemarc.core.domain.entityDTO

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.ACCOUNT_TABLE
import com.piconemarc.model.entity.AccountUiModel

@Entity(tableName = ACCOUNT_TABLE)
data class AccountDTO(
    @PrimaryKey(autoGenerate = true)
    override var id : Long = 0,
    override val name : String = "",
    val accountBalance : Double = 0.0,
    val accountOverdraft : Double = 0.0
) : DTO<AccountUiModel,AccountDTO> {
    override fun fromUiModel(model: AccountUiModel): AccountDTO {
        return this.copy(
            id = model.id,
            name = model.name,
            accountBalance = model.accountBalance,
            accountOverdraft = model.accountOverdraft
        )
    }

    override fun toUiModel(): AccountUiModel {
        return AccountUiModel(id, name, accountBalance, accountOverdraft)
    }
}