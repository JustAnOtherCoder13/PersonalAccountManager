package com.piconemarc.model.entity


val GeneratedAccount : MutableList<AccountModel> =  mutableListOf(
    AccountModel(id = 1,name = "myName1"),
    AccountModel(id = 2,name = "myName2"),
    AccountModel(id = 3,name = "myName3"),
)

val GeneratedOperation : MutableList<OperationModel> = mutableListOf(
    OperationModel(id = 1,name = "first", amount = 10.00),
    OperationModel(id = 2,name = "second",amount = 50.00),
    OperationModel(id = 3,name = "third",amount = -40.00)
)

val GeneratedCategory : MutableList<CategoryModel> = mutableListOf(
    CategoryModel(id = 1,name = "cat1"),
    CategoryModel(id = 2,name = "cat2"),
    CategoryModel(id = 3,name = "cat3")
)