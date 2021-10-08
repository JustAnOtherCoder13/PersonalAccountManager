package com.piconemarc.core.domain.entityDTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.OPERATION_TABLE
import java.util.*


@Entity(
    foreignKeys = [ForeignKey(
        entity = AccountDTO::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("accountId"),
        onDelete = CASCADE
    ),
    ForeignKey(
        entity = CategoryDTO::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId")
    )],
    tableName = OPERATION_TABLE,

)
data class OperationDTO(
    @PrimaryKey
    var id : Long = 0,
    val name : String = "",
    val amount : Double = 0.0,
    val endDateMonth : String = "",
    val endDateYear : String = "",
    val isRecurrent : Boolean = false,
    @ColumnInfo(index = true)
    val accountId : Long = 0,
    @ColumnInfo(index = true)
    val categoryId : Long = 0,
    val emitDate : Date? = Date()
)