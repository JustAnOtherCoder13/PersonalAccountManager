package com.piconemarc.core.domain.entityDTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.TRANSFER_TABLE


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = OperationDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("senderOperationId"),
        ),
        ForeignKey(
            entity = OperationDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("beneficiaryOperationId"),
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
)