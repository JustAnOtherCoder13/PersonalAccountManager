package com.piconemarc.core.domain

import com.piconemarc.model.entity.PresentationDataModel
import java.text.DateFormatSymbols
import java.util.*

object Constants {

    const val ACCOUNT_TABLE = "account_table"
    const val CATEGORY_TABLE = "category_table"
    const val OPERATION_TABLE = "operation_table"

    val TODAY = Calendar.getInstance().time

    val OPERATION_MODEL = PresentationDataModel(stringValue = "Operation")
    val PAYMENT_MODEL = PresentationDataModel(stringValue = "Payment")
    val TRANSFER_MODEL = PresentationDataModel(stringValue = "Transfer")

    val CATEGORY_MODEL = PresentationDataModel(stringValue = "Category")

    val SENDER_ACCOUNT_MODEL = PresentationDataModel(stringValue = "Sender account")
    val BENEFICIARY_ACCOUNT_MODEL = PresentationDataModel(stringValue = "Beneficiary account")



    val YEAR_MODEL = PresentationDataModel(stringValue = "Year")
    val MONTH_MODEL = PresentationDataModel(stringValue = "Month")


    val SELECTABLE_YEARS_LIST: List<PresentationDataModel> =
        mapFifteenNextYearToPresentationModelList()

    private fun mapFifteenNextYearToPresentationModelList(): List<PresentationDataModel> {
        val list: MutableList<PresentationDataModel> = mutableListOf()
        val actualYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in actualYear..(actualYear + 15)) list.add(
            PresentationDataModel(stringValue = i.toString())
        )
        return list
    }

    val SELECTABLE_MONTHS_LIST : List<PresentationDataModel> =
        DateFormatSymbols(Locale.FRENCH).months.toList().map { PresentationDataModel(stringValue = it )}

}