package com.piconemarc.model.entity

data class OperationModel(
    val operationName : String = "",
    val operationAmount : Double= 0.0,
    val category: CategoryModel = CategoryModel()
)
