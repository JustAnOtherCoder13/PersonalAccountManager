package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.account.UpdateAccountBalanceInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
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
) : ActionDispatcher {

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateAddOperationPopUpState(action))
        when (action) {
            is AppActions.AddOperationPopUpAction.SelectOptionIcon -> {
                //option selector
                /*when (action.selectedIcon) {
                    is PAMIconButtons.Payment -> updateState(
                        GlobalAction.UpdateAddOperationPopUpState(
                            AppActions.AddOperationPopUpAction.ExpandPaymentOption
                        ),
                        GlobalAction.UpdateAddOperationPopUpState(
                            AppActions.AddOperationPopUpAction.ExpandRecurrentOption
                        )
                    )
                    is PAMIconButtons.Transfer -> updateState(
                        GlobalAction.UpdateAddOperationPopUpState(
                            AppActions.AddOperationPopUpAction.ExpandTransferOption
                        ),
                        GlobalAction.UpdateAddOperationPopUpState(
                            AppActions.AddOperationPopUpAction.CloseRecurrentOption
                        )
                    )
                    else -> updateState(
                        GlobalAction.UpdateAddOperationPopUpState(
                            AppActions.AddOperationPopUpAction.CollapseOptions
                        ),
                        GlobalAction.UpdateAddOperationPopUpState(
                            AppActions.AddOperationPopUpAction.CloseRecurrentOption
                        )
                    )
                }*/
            }

            is AppActions.AddOperationPopUpAction.InitPopUp ->
                scope.launch {
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
            is AppActions.AddOperationPopUpAction.ExpandTransferOption -> {
                updateState(
                    GlobalAction.UpdateAddOperationPopUpState(
                        AppActions.AddOperationPopUpAction.SelectSenderAccount(
                            AppSubscriber.AppUiState.myAccountDetailScreenUiState.selectedAccount
                        )
                    )
                )
                scope.launch {
                    getAllAccountsInteractor.getAllAccounts()
                        .collect {
                            updateState(
                                GlobalAction.UpdateAddOperationPopUpState(
                                    AppActions.AddOperationPopUpAction.UpdateAccountList(
                                        it
                                    )
                                )
                            )
                        }
                }
            }
            is AppActions.AddOperationPopUpAction.AddNewOperation -> {

                if (!addOperationPopUpUiState.isOperationNameError
                    && !addOperationPopUpUiState.isOperationAmountError
                ){
                    scope.launch {
                        addNewOperationInteractor.addNewOperation(action.operation)
                    }

                }



                //todo review for transfer, add a transfer entity?
                /* {
                    scope.launch {
                        if (addOperationPopUpUiState.isTransferExpanded
                            && !addOperationPopUpUiState.isSenderAccountError
                            && !addOperationPopUpUiState.isBeneficiaryAccountError
                        ) {

                            val beneficiaryAccount =
                                getAccountForIdInteractor.getAccountForId(
                                    addOperationPopUpUiState.beneficiaryAccount.id
                                )
                            //add on beneficiary account
                            addNewOperationInteractor.addNewOperation(
                                action.operation.copy(
                                    accountId = beneficiaryAccount.id,
                                    beneficiaryAccountId = action.operation.id,
                                    isAddOperation = addOperationPopUpUiState.isAddOperation
                                )
                            )
                            beneficiaryAccount.addOperation(operationAmount = action.operation.updatedAmount)
                            updateAccountBalanceInteractor.updateAccountBalance(
                                updatedAccount = beneficiaryAccount
                            )
                        }


                        try {
                            addNewOperationInteractor.addNewOperation(
                                action.operation.copy(isAddOperation = addOperationPopUpUiState.isAddOperation)
                            )
                            val senderAccount =
                                getAccountForIdInteractor.getAccountForId(action.operation.accountId)

                            if (addOperationPopUpUiState.isAddOperation)
                                senderAccount.addOperation(action.operation.updatedAmount)
                            else senderAccount.minusOperation(action.operation.updatedAmount)

                            updateAccountBalanceInteractor.updateAccountBalance(
                                updatedAccount = senderAccount
                            )
                            updateState(
                                GlobalAction.UpdateAddOperationPopUpState(
                                    AppActions.AddOperationPopUpAction.ClosePopUp
                                )
                            )
                        } catch (e: java.lang.Exception) {
                            Log.e("TAG", "dispatchAction: ", e)
                        }
                    }
                }*/
            }
        }
    }
}