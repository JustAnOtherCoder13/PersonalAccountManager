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
            entity = AccountDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("senderAccountId"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = AccountDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("beneficiaryAccountId"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = AccountDTO::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("beneficiaryAccountId"),
            onDelete = CASCADE
        )
    ], tableName = TRANSFER_TABLE
)
data class TransferDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val name: String="",
    @ColumnInfo(index = true)
    val senderAccountId: Long=0,
    val senderOperationId: Long=0,
    @ColumnInfo(index = true)
    val beneficiaryAccountId: Long=0,
    val beneficiaryOperationId: Long=0,
)