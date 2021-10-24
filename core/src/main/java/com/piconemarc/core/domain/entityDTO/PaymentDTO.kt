package com.piconemarc.core.domain.entityDTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.PAYMENT_TABLE
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
    val endDate: Date = Date()
)