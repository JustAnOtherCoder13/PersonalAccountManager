package com.piconemarc.core.domain.entityDTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.utils.Constants.PAYMENT_TABLE
import com.piconemarc.model.entity.PaymentUiModel
import java.util.*


@Entity(foreignKeys = [
    ForeignKey(
        entity = OperationDTO::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("operationId"),
        onDelete = CASCADE
    ),
    ForeignKey(
        entity = AccountDTO::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("accountId"),
        onDelete = CASCADE
    )
]
, tableName = PAYMENT_TABLE
)
data class PaymentDTO(
    @PrimaryKey(autoGenerate = true)
    override val id : Long = 0,
    override val name : String = "",
    @ColumnInfo(index = true)
    val operationId : Long = 0,
    @ColumnInfo(index = true)
    val accountId : Long = 0,
    val endDate: Date? = null
) : DTO<PaymentUiModel,PaymentDTO>{
    override fun fromUiModel(model: PaymentUiModel): PaymentDTO{
        return this.copy(
            id = model.id,
            name = model.name,
            operationId = model.operationId,
            accountId = model.accountId,
            endDate = model.endDate
        )
    }
    override fun toUiModel() : PaymentUiModel{
        return PaymentUiModel(
            id = this.id,
            name = this.name,
            operationId = this.operationId,
            accountId = this.accountId,
            endDate = this.endDate
        )
    }
}