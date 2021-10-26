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
    val id: Long=0,
    val name: String="",
    @ColumnInfo(index = true)
    val senderOperationId: Long=0,
    @ColumnInfo(index = true)
    val beneficiaryOperationId: Long=0,
    @ColumnInfo(index = true)
    val paymentId : Long? = null
){
    fun fromUiModel(transferUiModel: TransferUiModel) : TransferDTO{
        return this.copy(
            id = transferUiModel.id,
            name = transferUiModel.name,
            senderOperationId = transferUiModel.senderOperationId,
            beneficiaryOperationId = transferUiModel.beneficiaryOperationId,
            paymentId = transferUiModel.paymentId
        )
    }
    fun toUiModel() : TransferUiModel{
        return TransferUiModel(
            id = this.id,
            name = this.name,
            senderOperationId = this.senderOperationId,
            beneficiaryOperationId = this.beneficiaryOperationId,
            paymentId = this.paymentId
        )
    }
}