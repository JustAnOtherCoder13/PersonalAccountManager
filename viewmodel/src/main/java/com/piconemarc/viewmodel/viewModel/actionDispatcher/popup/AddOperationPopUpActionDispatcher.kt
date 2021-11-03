package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.viewmodel.*
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val addNewOperationInteractor: AddNewOperationInteractor,

) : ActionDispatcher {

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateAddOperationPopUpState(action))
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
                scope.launchCatchingError(
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
                if (addOperationPopUpUiState.isOnPaymentScreen){
                    this.dispatchAction(
                        AppActions.AddOperationPopUpAction.ExpandRecurrentOption,
                        scope
                    )
                }
            }
            is AppActions.AddOperationPopUpAction.ExpandTransferOption -> {
                scope.launchCatchingError(
                    block = {
                        updateState(
                            GlobalAction.UpdateAddOperationPopUpState(
                                AppActions.AddOperationPopUpAction.UpdateAccountList(
                                    getAllAccountsInteractor.getAllAccounts().filter {
                                        it.id != myAccountDetailScreenUiState.selectedAccount.id
                                    }
                                )
                            )
                        )
                    }
                )
            }

            is AppActions.AddOperationPopUpAction.AddNewOperation<*> -> {
                //if no error on name and amount
                if (!addOperationPopUpUiState.isOperationNameError
                    && !addOperationPopUpUiState.isOperationAmountError
                ) {
                    //Check selected icon ------------------------------------------------------------
                    if (!addOperationPopUpUiState.isOnPaymentScreen) {
                        action.operation as OperationUiModel
                        when (addOperationPopUpUiState.addPopUpOptionSelectedIcon) {
                            is PAMIconButtons.Operation -> {
                                scope.launchOnIOCatchingError(
                                    block = { addNewOperationInteractor.addOperation(action.operation) },
                                    doOnSuccess = { closePopUp() }
                                )
                            }
                            is PAMIconButtons.Payment -> {
                                scope.launchOnIOCatchingError(
                                    block = {
                                        addNewOperationInteractor.addPaymentAndOperation(
                                            operation = action.operation,
                                            endDate = try {
                                                SimpleDateFormat("MMMM/yyyy", Locale.FRANCE).parse(
                                                    addOperationPopUpUiState.enDateSelectedMonth
                                                            + "/" + addOperationPopUpUiState.endDateSelectedYear,
                                                )
                                            } catch (e: ParseException) {
                                                null
                                            }
                                        )
                                    },
                                    doOnSuccess = { closePopUp() }
                                )
                            }
                            is PAMIconButtons.Transfer -> {
                                if (!addOperationPopUpUiState.isBeneficiaryAccountError) {
                                    scope.launchOnIOCatchingError(
                                        block = {
                                            addNewOperationInteractor.addTransferOperation(
                                                operation = action.operation,
                                                beneficiaryAccountId = addOperationPopUpUiState.beneficiaryAccount.id
                                            )
                                        },
                                        doOnSuccess = { closePopUp() }
                                    )
                                }
                            }
                            else -> {
                                closePopUp()
                            }
                        }
                    } else {
                        action.operation as PaymentUiModel
                        if (addOperationPopUpUiState.isPaymentStartThisMonth){
                            //add payment and related operation
                                scope.launchOnIOCatchingError(
                                    block = {
                                        addNewOperationInteractor.addPaymentAndOperation(
                                            OperationUiModel(
                                                accountId = action.operation.accountId ,
                                                name = action.operation.name,
                                                amount = action.operation.amount,
                                                categoryId = addOperationPopUpUiState.selectedCategory.id ,
                                                isAddOperation = addOperationPopUpUiState.isAddOperation,
                                            ),
                                            endDate = try {
                                                SimpleDateFormat("MMMM/yyyy", Locale.FRANCE).parse(
                                                    addOperationPopUpUiState.enDateSelectedMonth
                                                            + "/" + addOperationPopUpUiState.endDateSelectedYear,
                                                )
                                            } catch (e: ParseException) {
                                                null
                                            }
                                        )
                                    },
                                    doOnSuccess = {closePopUp()}
                                )

                        }
                        else{
                            scope.launchOnIOCatchingError(
                                block = {
                                    addNewOperationInteractor.addNewPayment(
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

    private fun closePopUp() {
        updateState(
            GlobalAction.UpdateAddOperationPopUpState(
                AppActions.AddOperationPopUpAction.ClosePopUp
            )
        )
    }
}