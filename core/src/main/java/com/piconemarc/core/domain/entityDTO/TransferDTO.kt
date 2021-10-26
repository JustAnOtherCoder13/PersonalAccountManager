package com.piconemarc.core.domain.entityDTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.TRANSFER_TABLE
import com.piconemarc.model.entity.TransferUiModel

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = OperationDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("senderOperationId"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = OperationDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("beneficiaryOperationId"),
            onDelete = CASCADE
        )
    ], tableName = TRANSFER_TABLE
)
data class TransferDTO(
    @PrimaryKey(autoGenerate = true)
    override val id: Long=0,
    override val name: String="",
    @ColumnInfo(index = true)
    val senderOperationId: Long=0,
    @ColumnInfo(index = true)
    val beneficiaryOperationId: Long=0,
    @ColumnInfo(index = true)
    val paymentId : Long? = null
) : DTO<TransferUiModel,TransferDTO>{

    override fun fromUiModel(model: TransferUiModel) : TransferDTO{
        return this.copy(
            id = model.id,
            name = model.name,
            senderOperationId = model.senderOperationId,
            beneficiaryOperationId = model.beneficiaryOperationId,
            paymentId = model.paymentId
        )
    }
    override fun toUiModel() : TransferUiModel{
        return TransferUiModel(
            id = this.id,
            name = this.name,
            senderOperationId = this.senderOperationId,
            beneficiaryOperationId = this.beneficiaryOperationId,
            paymentId = this.paymentId
        )
    }
}