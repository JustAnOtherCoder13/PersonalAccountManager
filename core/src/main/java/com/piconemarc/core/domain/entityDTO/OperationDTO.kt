package com.piconemarc.core.domain.entityDTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.piconemarc.core.domain.Constants.OPERATION_TABLE
import com.piconemarc.model.entity.OperationModel


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
data class OperationDTO(val operationModel: OperationModel) {
    @PrimaryKey
    val id : Long = operationModel.id
    val name : String = operationModel.name
    val amount : Double = operationModel.amount
    val endDateMonth : String = operationModel.endDate.month
    val endDateYear : String = operationModel.endDate.year
    val isRecurrent : Boolean = operationModel.isRecurrent
    @ColumnInfo(index = true)
    val accountId : Long = operationModel.AccountId
    @ColumnInfo(index = true)
    val categoryId : Long = operationModel.CategoryId
}