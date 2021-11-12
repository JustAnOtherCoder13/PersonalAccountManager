package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.core.domain.interactor.payment.AddNewPaymentInteractor
import com.piconemarc.core.domain.interactor.payment.AddPaymentAndOperationInteractor
import com.piconemarc.core.domain.interactor.transfer.AddNewTransferInteractor
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.addOperationPopUpVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val addNewOperationInteractor: AddNewOperationInteractor,
    private val addNewPaymentInteractor: AddNewPaymentInteractor,
    private val addPaymentAndOperationInteractor: AddPaymentAndOperationInteractor,
    private val addNewTransferInteractor: AddNewTransferInteractor

) : ActionDispatcher<ViewModelInnerStates.AddOperationPopUpVMState> {

    override val state: MutableStateFlow<ViewModelInnerStates.AddOperationPopUpVMState> =
        addOperationPopUpVMState_
    override val uiState: MutableState<ViewModelInnerStates.AddOperationPopUpVMState> =
        mutableStateOf(
            state.value
        )

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {

        updateState(GlobalAction.UpdateAddOperationPopUpState(action))
        scope.launch { state.collectLatest { uiState.value = it } }

        when (action) {
            is AppActions.AddOperationPopupAction.InitPopUp -> {
                scope.launchOnIOCatchingError(
                    block = {
                        val selectedAccount =
                            getAccountForIdInteractor.getAccountForId(action.selectedAccountId)
                        val allAccounts = getAllAccountsInteractor.getAllAccounts()

                        getAllCategoriesInteractor.getAllCategoriesAsFlow(this)
                            .collectLatest { allCategories ->
                                dispatchAction(
                                    AppActions.AddOperationPopupAction.UpdateState(
                                        allCategories = allCategories,
                                        allAccounts = allAccounts.filter { account -> account.id != selectedAccount.id },
                                        selectedAccount = selectedAccount
                                    ),
                                    this
                                )
                            }
                    }
                )
            }

            is AppActions.AddOperationPopupAction.AddOperation -> {
                dispatchAction(
                    AppActions.AddOperationPopupAction.CheckError(
                        operationName = action.newOperation.name,
                        operationAmount = action.newOperation.amount.toString(),
                    ), scope
                )
                if (!action.isOperationError) {
                    scope.launchOnIOCatchingError(
                        block = {
                            addNewOperationInteractor.addOperation(action.newOperation)
                        },
                        doOnSuccess = { closePopUp() }
                    )
                }
            }

            is AppActions.AddOperationPopupAction.AddPayment -> {
                dispatchAction(
                    AppActions.AddOperationPopupAction.CheckError(
                        operationName = action.newOperation.name,
                        operationAmount = action.newOperation.amount.toString(),
                        paymentEndDate = action.paymentEndDate
                    ), scope
                )
                if (!action.isOperationError)
                    if (action.isOnPaymentScreen && !action.isPaymentStartThisMonth) {
                        //just add payment
                        scope.launchOnIOCatchingError(
                            block = {
                                addNewPaymentInteractor.addNewPayment(
                                    PaymentUiModel(
                                        name = action.newOperation.name,
                                        operationId = null,
                                        amount = action.newOperation.amount,
                                        accountId = action.newOperation.accountId,
                                        endDate = getFormattedEndDateOrNull(
                                            action.paymentEndDate.first,
                                            action.paymentEndDate.second
                                        )
                                    )
                                )
                            },
                            doOnSuccess = { closePopUp() }
                        )
                    } else {
                        // when on operation detail screen or payment start this month
                        scope.launchOnIOCatchingError(
                            block = {
                                addPaymentAndOperationInteractor.addPaymentAndOperation(
                                    operation = action.newOperation,
                                    endDate = getFormattedEndDateOrNull(
                                        action.paymentEndDate.first,
                                        action.paymentEndDate.second
                                    )
                                )
                            },
                            doOnSuccess = { closePopUp() }
                        )
                    }
            }
            is AppActions.AddOperationPopupAction.AddTransfer -> {
                //check error
                dispatchAction(
                    AppActions.AddOperationPopupAction.CheckError(
                        operationName = action.newOperation.name,
                        operationAmount = action.newOperation.amount.toString(),
                        beneficiaryAccount = action.beneficiaryAccount
                    ), scope
                )
                if (!action.isOperationError) {
                    scope.launchOnIOCatchingError(
                        block = {
                            addNewTransferInteractor.addTransferOperation(
                                operation = action.newOperation,
                                beneficiaryAccountId = action.beneficiaryAccount.id
                            )
                        },
                        doOnSuccess = { closePopUp() }
                    )
                }
            }

        }
    }

    private fun getFormattedEndDateOrNull(month: String, year: String) = try {
        SimpleDateFormat("MMMM/yyyy", Locale.FRANCE).parse(
            month
                    + "/" + year,
        )
    } catch (e: ParseException) {
        null
    }

    private fun closePopUp() {
        updateState(
            GlobalAction.UpdateAddOperationPopUpState(
                AppActions.AddOperationPopupAction.ClosePopUp
            )
        )
    }
}