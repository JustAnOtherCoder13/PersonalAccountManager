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
    )],
    tableName = OPERATION_TABLE,

)
data class OperationDTO(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String = "",
    val amount : Double = 0.0,
    @ColumnInfo(index = true)
    val accountId : Long = 0,
    @ColumnInfo(index = true)
    val categoryId : Long = 1,
    val emitDate : Date? = Date()
){
    @Ignore
    fun fromOperationModel(operationUiModel: OperationUiModel) : OperationDTO{
       return this.copy(
            name = operationUiModel.name,
           amount = operationUiModel.amount,
           accountId = operationUiModel.accountId,
           categoryId = operationUiModel.categoryId,
           emitDate = operationUiModel.emitDate
        )
    }

    @Ignore
    fun toOperationUiModel() : OperationUiModel{
        return OperationUiModel(id, accountId, name, amount, categoryId , emitDate?:Date()       )
    }
}