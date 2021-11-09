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

) : ActionDispatcher<ViewModelInnerStates.AddOperationPopUpVMState> {

    override val state : MutableStateFlow<ViewModelInnerStates.AddOperationPopUpVMState> = addOperationPopUpVMState_
    override val uiState : MutableState<ViewModelInnerStates.AddOperationPopUpVMState> = mutableStateOf(
        state.value)

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {

        updateState(GlobalAction.UpdateAddOperationPopUpState(action))
        scope.launch { state.collectLatest { uiState.value = it }}

        when (action) {
            is AppActions.AddOperationPopUpAction.SelectOptionIcon -> AddPopUpOptionNavigation(action, scope)
            is AppActions.AddOperationPopUpAction.InitPopUp -> {
                scope.launchOnIOCatchingError(
                    block = {
                        getAllCategoriesInteractor.getAllCategoriesAsFlow(this).collectLatest {
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
                if (action.isOnPaymentScreen){
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
                scope.launchOnIOCatchingError(
                    block = {
                        updateState(
                            GlobalAction.UpdateAddOperationPopUpState(
                                AppActions.AddOperationPopUpAction.UpdateAccountList(
                                    getAllAccountsInteractor.getAllAccounts()/*.filter {
                                    //todo pass with own state
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
                if (action.operation.name.trim().isNotEmpty()
                    && action.operation.amount != 0.0
                ) {
                    //if not on payment screen means that we are on account detail
                    //so check selected icon and add required operation
                    if (!action.isOnPaymentScreen) {
                        action.operation as OperationUiModel
                        when (uiState.value.addPopUpOptionSelectedIcon) {
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
                                if (!uiState.value.isBeneficiaryAccountError) {
                                    scope.launchOnIOCatchingError(
                                        block = {
                                            addNewTransferInteractor.addTransferOperation(
                                                operation = action.operation,
                                                beneficiaryAccountId = uiState.value.beneficiaryAccount.id
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
                        if (uiState.value.isPaymentStartThisMonth){
                            //add payment and related operation
                                scope.launchOnIOCatchingError(
                                    block = {
                                        addPaymentAndOperationInteractor.addPaymentAndOperation(
                                            OperationUiModel(
                                                accountId = action.operation.accountId ,
                                                name = action.operation.name,
                                                amount = action.operation.amount,
                                                categoryId = uiState.value.selectedCategory.id ,
                                                isAddOperation = uiState.value.isAddOperation,
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

    private fun AddPopUpOptionNavigation(
        action: AppActions.AddOperationPopUpAction.SelectOptionIcon,
        scope: CoroutineScope
    ) {
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

    private fun getFormattedEndDateOrNull() = try {
        SimpleDateFormat("MMMM/yyyy", Locale.FRANCE).parse(
            uiState.value.enDateSelectedMonth
                    + "/" + uiState.value.endDateSelectedYear,
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