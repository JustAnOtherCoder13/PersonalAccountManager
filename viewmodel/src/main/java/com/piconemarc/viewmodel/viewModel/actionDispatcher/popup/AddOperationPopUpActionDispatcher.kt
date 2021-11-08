package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.core.domain.interactor.payment.AddNewPaymentInteractor
import com.piconemarc.core.domain.interactor.payment.AddPaymentAndOperationInteractor
import com.piconemarc.core.domain.interactor.transfer.AddNewTransferInteractor
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.addOperationPopUpVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
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
    private val addNewOperationInteractor: AddNewOperationInteractor,
    private val addNewPaymentInteractor: AddNewPaymentInteractor,
    private val addPaymentAndOperationInteractor: AddPaymentAndOperationInteractor,
    private val addNewTransferInteractor: AddNewTransferInteractor

) : ActionDispatcher {

    //todo state as to be in global scope
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        //updateState(GlobalAction.UpdateAddOperationPopUpState(action))
        val state : MutableStateFlow<ViewModelInnerStates.AddOperationPopUpVMState> = addOperationPopUpVMState_
        val uiState : MutableState<ViewModelInnerStates.AddOperationPopUpVMState> = mutableStateOf(
            state.value)
        scope.launch {
            state.collectLatest {
                uiState.value = it
            }
        }

        when (action) {
            is AppActions.AddOperationPopUpAction.SelectOptionIcon -> {
                //option selector on change redispatch action in same class to trigger interactors
                when (action.selectedIcon) {
                    is PAMIconButtons.Payment -> {
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandPaymentOption,
                            scope
                        )
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandRecurrentOption,
                            scope
                        )
                    }

                    is PAMIconButtons.Transfer -> {
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandTransferOption,
                            scope
                        )
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.CloseRecurrentOption,
                            scope
                        )
                    }
                    else -> {
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.CollapseOptions,
                            scope
                        )
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.CloseRecurrentOption,
                            scope
                        )
                    }

                }
            }
            is AppActions.AddOperationPopUpAction.InitPopUp -> {
               // updateState(GlobalAction.UpdateAddOperationPopUpState(action))
                scope.launchUnconfinedCatchingError(
                    block = {
                        getAllCategoriesInteractor.getAllCategories().collect {
                            updateState(
                                GlobalAction.UpdateAddOperationPopUpState(
                                    AppActions.AddOperationPopUpAction.UpdateCategoriesList(
                                        it
                                    )
                                )
                            )
                        }
                    }
                )

                if (uiState.value.isOnPaymentScreen){
                    this.dispatchAction(
                        AppActions.AddOperationPopUpAction.ExpandRecurrentOption,
                        scope
                    )
                }
                this.dispatchAction(
                    AppActions.AddOperationPopUpAction.SelectAddOrMinus(false),
                    scope
                )
            }
            is AppActions.AddOperationPopUpAction.ExpandTransferOption -> {
                scope.launchUnconfinedCatchingError(
                    block = {
                        updateState(
                            GlobalAction.UpdateAddOperationPopUpState(
                                AppActions.AddOperationPopUpAction.UpdateAccountList(
                                    getAllAccountsInteractor.getAllAccounts()/*.filter {
                                        it.id != myAccountDetailScreenUiState.value.selectedAccount.id
                                    }*/
                                )
                            )
                        )
                    }
                )
            }

            is AppActions.AddOperationPopUpAction.AddNewOperation<*> -> {
                //if no error on name and amount
                if (!addOperationPopUpUiState.value.isOperationNameError
                    && !addOperationPopUpUiState.value.isOperationAmountError
                ) {
                    //if not on payment screen means that we are on account detail
                    //so check selected icon and add required operation
                    if (!addOperationPopUpUiState.value.isOnPaymentScreen) {
                        action.operation as OperationUiModel
                        when (addOperationPopUpUiState.value.addPopUpOptionSelectedIcon) {
                            is PAMIconButtons.Operation -> {
                                scope.launchOnIOCatchingError(
                                    block = { addNewOperationInteractor.addOperation(action.operation) },
                                    doOnSuccess = { closePopUp() }
                                )
                            }
                            is PAMIconButtons.Payment -> {
                                scope.launchOnIOCatchingError(
                                    block = {
                                        addPaymentAndOperationInteractor.addPaymentAndOperation(
                                            operation = action.operation,
                                            endDate = getFormattedEndDateOrNull()
                                        )
                                    },
                                    doOnSuccess = { closePopUp() }
                                )
                            }
                            is PAMIconButtons.Transfer -> {
                                if (!addOperationPopUpUiState.value.isBeneficiaryAccountError) {
                                    scope.launchOnIOCatchingError(
                                        block = {
                                            addNewTransferInteractor.addTransferOperation(
                                                operation = action.operation,
                                                beneficiaryAccountId = addOperationPopUpUiState.value.beneficiaryAccount.id
                                            )
                                        },
                                        doOnSuccess = { closePopUp() }
                                    )
                                }
                            }
                            else -> { closePopUp() }
                        }
                    }
                    // else we are on paymentScreen
                    else {
                        action.operation as PaymentUiModel
                        //if payment start this month, add payment and related operation
                        if (addOperationPopUpUiState.value.isPaymentStartThisMonth){
                            //add payment and related operation
                                scope.launchOnIOCatchingError(
                                    block = {
                                        addPaymentAndOperationInteractor.addPaymentAndOperation(
                                            OperationUiModel(
                                                accountId = action.operation.accountId ,
                                                name = action.operation.name,
                                                amount = action.operation.amount,
                                                categoryId = addOperationPopUpUiState.value.selectedCategory.id ,
                                                isAddOperation = addOperationPopUpUiState.value.isAddOperation,
                                            ),
                                            endDate = getFormattedEndDateOrNull()
                                        )
                                    },
                                    doOnSuccess = {closePopUp()}
                                )
                        }
                        //else only add payment
                        else{
                            scope.launchOnIOCatchingError(
                                block = {
                                    addNewPaymentInteractor.addNewPayment(
                                        action.operation
                                    )
                                },
                                doOnSuccess = {closePopUp()}
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getFormattedEndDateOrNull() = try {
        SimpleDateFormat("MMMM/yyyy", Locale.FRANCE).parse(
            addOperationPopUpUiState.value.enDateSelectedMonth
                    + "/" + addOperationPopUpUiState.value.endDateSelectedYear,
        )
    } catch (e: ParseException) {
        null
    }

    private fun closePopUp() {
        updateState(
            GlobalAction.UpdateAddOperationPopUpState(
                AppActions.AddOperationPopUpAction.ClosePopUp
            )
        )
    }
}