package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import android.util.Log
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.account.UpdateAccountBalanceInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.viewmodel.ComponentActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val addNewOperationInteractor: AddNewOperationInteractor,
    private val updateAccountBalanceInteractor: UpdateAccountBalanceInteractor,
    private val getAccountForIdInteractor: GetAccountForIdInteractor
) : ComponentActionDispatcher {

    private var getAllCategoriesJob: Job? = null
    private var addOperationPopUpAccountJob: Job? = null

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(
            GlobalAction.UpdateAddOperationPopUpState(action))
        when (action) {

            is  AppActions.AddOperationPopUpAction.SelectOptionIcon -> {
                updateState(
                    GlobalAction.UpdateAddOperationPopUpState(
                    AppActions.AddOperationPopUpAction.ExpandRecurrentOption
                ))
                when (action.selectedIcon) {
                    is PAMIconButtons.Payment -> updateState(
                        GlobalAction.UpdateAddOperationPopUpState(
                        AppActions.AddOperationPopUpAction.ExpandPaymentOption
                    ))
                    is PAMIconButtons.Transfer -> updateState(
                        GlobalAction.UpdateAddOperationPopUpState(
                        AppActions.AddOperationPopUpAction.ExpandTransferOption
                    ))
                    else -> updateState(
                        GlobalAction.UpdateAddOperationPopUpState(
                        AppActions.AddOperationPopUpAction.CollapseOptions
                    ))
                }
            }

            is AppActions.AddOperationPopUpAction.InitPopUp ->
                getAllCategoriesJob = scope.launch {
                    getAllCategoriesInteractor.getAllCategoriesToDataUiModelList().collect {
                        updateState(
                            GlobalAction.UpdateAddOperationPopUpState(
                            AppActions.AddOperationPopUpAction.UpdateCategoriesList(
                                it
                            )
                        ))
                    }
                }

            is AppActions.AddOperationPopUpAction.ExpandTransferOption -> {
                addOperationPopUpAccountJob = scope.launch {
                    getAllAccountsInteractor.getAllAccountsToPresentationDataModel()
                        .collect {
                            updateState(
                                GlobalAction.UpdateAddOperationPopUpState(
                                AppActions.AddOperationPopUpAction.UpdateAccountList(
                                    it
                                )
                            ))
                        }
                }
                updateState(
                    GlobalAction.UpdateAddOperationPopUpState(
                    AppActions.AddOperationPopUpAction.SelectSenderAccount(
                        AppSubscriber.AppUiState.myAccountDetailScreenUiState.accountName
                    )
                ))
            }

            is AppActions.AddOperationPopUpAction.ClosePopUp -> {
                addOperationPopUpAccountJob?.cancel()
                getAllCategoriesJob?.cancel()
            }
            is AppActions.AddOperationPopUpAction.AddNewOperation -> {

                //todo review for transfer, add a transfer entity?
                if (!AppSubscriber.AppUiState.addOperationPopUpUiState.isOperationNameError
                    && !AppSubscriber.AppUiState.addOperationPopUpUiState.isOperationAmountError
                ) {
                    scope.launch {
                        if (AppSubscriber.AppUiState.addOperationPopUpUiState.isTransferExpanded
                            && !AppSubscriber.AppUiState.addOperationPopUpUiState.isSenderAccountError
                            && !AppSubscriber.AppUiState.addOperationPopUpUiState.isBeneficiaryAccountError
                        ) {
                            scope.launch {
                                val beneficiaryAccount =
                                    getAccountForIdInteractor.getAccountForId(
                                        AppSubscriber.AppUiState.addOperationPopUpUiState.beneficiaryAccount.objectIdReference
                                    )
                                //add on beneficiary account
                                addNewOperationInteractor.addNewOperation(
                                    action.operation.copy(
                                        accountId = beneficiaryAccount.id,
                                        amount = action.operation.amount,
                                        beneficiaryAccountId = action.operation.id
                                    )
                                )
                                updateAccountBalanceInteractor.updateAccountBalanceOnAddOperation(
                                    accountId = beneficiaryAccount.id,
                                    oldAccountBalance = beneficiaryAccount.accountBalance,
                                    addedOperationAmount = action.operation.amount
                                )
                            }

                        }
                        try {
                            addNewOperationInteractor.addNewOperation(
                                if (AppSubscriber.AppUiState.addOperationPopUpUiState.isAddOperation && !AppSubscriber.AppUiState.addOperationPopUpUiState.isTransferExpanded) action.operation
                                else action.operation.copy(amount = action.operation.amount * -1)
                            )
                            updateAccountBalanceInteractor.updateAccountBalanceOnAddOperation(
                                accountId = action.operation.accountId,
                                oldAccountBalance = AppSubscriber.AppUiState.myAccountDetailScreenUiState.accountBalance.stringValue.toDouble(),
                                addedOperationAmount = if (AppSubscriber.AppUiState.addOperationPopUpUiState.isAddOperation) action.operation.amount
                                else action.operation.amount * -1
                            )
                            updateState(
                                GlobalAction.UpdateAddOperationPopUpState(
                                AppActions.AddOperationPopUpAction.ClosePopUp
                            ))
                        } catch (e: java.lang.Exception) {
                            Log.e("TAG", "dispatchAction: ", e)
                        }
                    }
                }
            }
        }
    }
}