package com.piconemarc.core.domain

import com.piconemarc.model.entity.PresentationDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    interface Interactor

    data class ErrorHandlerFlowCollector<T : Any>(
        private val scope: CoroutineScope,
        private val flow: Flow<T>
    ) {

        private fun onSuccess(data: T): T = data

        private fun onError(throwable: Throwable) {
            println("error : $throwable")
        }

        fun launchCoroutine(updatedData: (data: T) -> Unit) = scope.launch {
            flow
                .catch {
                    onError(it)
                }
                .collect {
                    updatedData(it)
                    onSuccess(it)
                }
        }
    }
}