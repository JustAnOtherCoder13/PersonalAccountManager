package com.piconemarc.model.entity

import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel

val TEST_OPERATION_MODEL : MutableList<OperationModel> = mutableListOf(
    OperationModel("first", 10.00),
    OperationModel("second",50.00),
    OperationModel("third",-40.00)
)

val testCategory : MutableList<CategoryModel> = mutableListOf(
    CategoryModel("cat1"),
    CategoryModel("cat2"),
    CategoryModel("cat3")
)