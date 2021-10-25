package com.piconemarc.core.domain.entityDTO

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.piconemarc.core.domain.Constants.PAYMENT_TABLE
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
    val id : Long = 0,
    val name : String = "",
    @ColumnInfo(index = true)
    val operationId : Long = 0,
    @ColumnInfo(index = true)
    val accountId : Long = 0,
    val endDate: Date? = null
){
    @Ignore
    fun fromPaymentModel(paymentUiModel: PaymentUiModel): PaymentDTO{
        return PaymentDTO(
            name = paymentUiModel.name,
            operationId = paymentUiModel.operationId,
            accountId = paymentUiModel.accountId,
            endDate = paymentUiModel.endDate
        )
    }
}