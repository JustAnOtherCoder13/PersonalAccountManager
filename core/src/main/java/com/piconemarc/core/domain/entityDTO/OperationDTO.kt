package com.piconemarc.core.domain.entityDTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.utils.Constants.OPERATION_TABLE
import com.piconemarc.model.entity.OperationUiModel
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = AccountDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("accountId"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = CategoryDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = SET_NULL
        ),
        ForeignKey(
            entity = PaymentDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("paymentId"),
            onDelete = SET_NULL
        ),
        ForeignKey(
            entity = TransferDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("transferId"),
            onDelete = CASCADE
        )],
    tableName = OPERATION_TABLE,
    )
data class OperationDTO(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0,
    override val name: String = "",
    val amount: Double = 0.0,
    @ColumnInfo(index = true)
    val accountId: Long = 0,
    @ColumnInfo(index = true)
    val categoryId: Long = 1,
    val emitDate: Date? = null,
    @ColumnInfo(index = true)
    val paymentId : Long? = null,
    @ColumnInfo(index = true)
    val transferId : Long? = null,
) : DTO<OperationUiModel,OperationDTO> {

    override fun fromUiModel(model: OperationUiModel): OperationDTO {
        return this.copy(
            id = model.id,
            name = model.name,
            amount = model.amount,
            accountId = model.accountId,
            categoryId = model.categoryId,
            emitDate = model.emitDate,
            paymentId = model.paymentId,
            transferId = model.transferId
        )
    }

    override fun toUiModel(): OperationUiModel {
        return OperationUiModel(
            id = id,
            accountId = accountId,
            name = name,
            amount = amount,
            categoryId = categoryId,
            emitDate = emitDate ?: Date(),
            paymentId = paymentId,
            transferId = transferId
        )
    }
}