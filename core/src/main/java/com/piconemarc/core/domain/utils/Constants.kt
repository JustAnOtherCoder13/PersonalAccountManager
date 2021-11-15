package com.piconemarc.core.domain.utils

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

    val TODAY: Date = Calendar.getInstance().time

    const val OPERATION =  "Operation"
    const val PAYMENT =  "Payment"
    const val TRANSFER =  "Transfer"

    val CATEGORY_MODEL =  "Category"

    const val BENEFICIARY_ACCOUNT =  "Beneficiary account"



    const val YEAR =  "Year"
    const val MONTH =  "Month"


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