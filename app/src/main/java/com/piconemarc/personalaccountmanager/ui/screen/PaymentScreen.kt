package com.piconemarc.personalaccountmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItTitle
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PostItBackground
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.VerticalDispositionSheet
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItInitialHeight
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItItemHeight
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItWidth
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MyPaymentScreen(
    paymentState : ViewModelInnerStates.PaymentScreenVmState,
    onAddPaymentButtonClick : (AppActions.AddOperationPopupAction)->Unit,
    onDeletePaymentButtonClick : (AppActions.DeleteOperationPopUpAction)-> Unit,
    onPaymentEvent : (AppActions.PaymentScreenAction)-> Unit
) {
    val listState = rememberLazyListState()
    //todo remember position to stay in background.

    VerticalDispositionSheet(
        body = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = RegularMarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                itemsIndexed(paymentState.allAccounts)
                {index, accountWithRelatedPayments ->
                    Box(
                        modifier = Modifier
                            .width(paymentPostItWidth)
                            .height(
                                paymentPostItInitialHeight
                                        + (accountWithRelatedPayments.relatedPayment.count()
                                        * paymentPostItItemHeight).dp
                            )
                            .padding(vertical = RegularMarge)
                    ) {
                        PostItBackground(this)
                        VerticalDispositionSheet(
                            header = {
                                PaymentPostItTitle(
                                    onAddPaymentButtonClick = {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            listState.scrollToItem(index)
                                        }

                                        onAddPaymentButtonClick(
                                            AppActions.AddOperationPopupAction.InitPopUp(
                                                isOnPaymentScreen = true,
                                                selectedAccountId = accountWithRelatedPayments.account.id
                                            )
                                        )
                                    },
                                    accountWithRelatedPayments = accountWithRelatedPayments,
                                    areAllPaymentForAccountPassedThisMonth = arePaymentPassedThisMonth(accountWithRelatedPayments.relatedPayment.map { it.isPaymentPassForThisMonth }) ,
                                    onPassAllPaymentButtonClick = onPaymentEvent
                                )
                            },
                            body = {
                                PaymentPostItBody(
                                    accountWithRelatedPayments = accountWithRelatedPayments,
                                    onDeletePaymentButtonClick = { paymentToDelete ->
                                        onDeletePaymentButtonClick(
                                            AppActions.DeleteOperationPopUpAction.InitPopUp(
                                                paymentToDelete
                                            )
                                        )
                                    },
                                    onPaymentEvent = onPaymentEvent
                                )
                            },
                            footer = { PaymentPostItFooter(accountWithRelatedPayments) }
                        )
                    }
                }
            }
        }
    )
}

//todo pass cleaner way
fun arePaymentPassedThisMonth (arePaymentPassed :  List<Boolean>) : Boolean{
    var mybol = false
    if (arePaymentPassed.isEmpty())
        mybol = true
    else
    arePaymentPassed.forEach {
        if (it) mybol = true
    }
    return mybol
}
