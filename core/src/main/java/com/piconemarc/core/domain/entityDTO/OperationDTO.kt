package com.piconemarc.core.domain.entityDTO

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.piconemarc.core.domain.Constants.OPERATION_TABLE
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
            childColumns = arrayOf("categoryId")
        ),
        ForeignKey(
            entity = PaymentDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("paymentId")
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
    val id: Long = 0,
    val name: String = "",
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
) {
    @Ignore
    fun fromOperationModel(operationUiModel: OperationUiModel): OperationDTO {
        return this.copy(
            id = operationUiModel.id,
            name = operationUiModel.name,
            amount = operationUiModel.amount,
            accountId = operationUiModel.accountId,
            categoryId = operationUiModel.categoryId,
            emitDate = operationUiModel.emitDate,
            paymentId = operationUiModel.paymentId,
            transferId = operationUiModel.transferId
        )
    }

    @Ignore
    fun toOperationUiModel(): OperationUiModel {
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