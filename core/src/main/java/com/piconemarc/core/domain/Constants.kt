package com.piconemarc.core.domain

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
    const val TRANSFER_TABLE = "transfer_table"
    const val PAYMENT_TABLE = "payment_table"

    val TODAY = Calendar.getInstance().time

    val OPERATION_MODEL =  "Operation"
    val PAYMENT_MODEL =  "Payment"
    val TRANSFER_MODEL =  "Transfer"

    val CATEGORY_MODEL =  "Category"

    val SENDER_ACCOUNT_MODEL =  "Sender account"
    val BENEFICIARY_ACCOUNT_MODEL =  "Beneficiary account"



    val YEAR_MODEL =  "Year"
    val MONTH_MODEL =  "Month"


    val SELECTABLE_YEARS_LIST: List<String> =
        mapFifteenNextYearToPresentationModelList()

    private fun mapFifteenNextYearToPresentationModelList(): List<String> {
        val list: MutableList<String> = mutableListOf()
        val actualYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in actualYear..(actualYear + 15)) list.add(
            i.toString()
        )
        return list
    }

    val SELECTABLE_MONTHS_LIST : List<String> =
        DateFormatSymbols(Locale.FRENCH).months.toList()

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