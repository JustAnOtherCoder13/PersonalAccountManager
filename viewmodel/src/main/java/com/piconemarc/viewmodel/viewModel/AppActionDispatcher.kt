package com.piconemarc.viewmodel.viewModel

import android.util.Log
import com.piconemarc.core.domain.interactor.account.AddNewAccountInteractor
import com.piconemarc.core.domain.interactor.account.DeleteAccountInteractor
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetAllOperationsForAccountIdInteractor
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.StoreSubscriber
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.addAccountPopUpUiState
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.deleteAccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AppActionDispatcher @Inject constructor(
    override val store: DefaultStore<ViewModelInnerStates.GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val deleteAccountInteractor: DeleteAccountInteractor,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val addNewAccountInteractor: AddNewAccountInteractor,
    private val getAllOperationsForAccountIdInteractor: GetAllOperationsForAccountIdInteractor,
    private val deleteOperationInteractor: DeleteOperationInteractor
) : ActionDispatcher<UiAction, ViewModelInnerStates.GlobalVmState>() {

    override val subscriber: StoreSubscriber<ViewModelInnerStates.GlobalVmState> =
        AppSubscriber().appStoreSubscriber

    //flow job
    private var getAllAccountsJob: Job? = null
    private var getAllCategoriesJob: Job? = null
    private var myAccountScreenJob: Job? = null
    private var myAccountDetailScreenJob: Job? = null

    override fun dispatchAction(action: UiAction) {
        store.add(subscriber)
        when (action) {

            //BaseAppScreen -----------------------------------------------------------------------------------
            is AppActions.BaseAppScreenAction -> {
                updateBaseAppState(action)
                when (action) {
                    is AppActions.BaseAppScreenAction.InitScreen -> {
                        getAllAccountsJob = scope.launch {
                            getAllAccountsInteractor.getAllAccounts().collect { allAccounts ->
                                updateBaseAppState(
                                    AppActions.BaseAppScreenAction.UpdateFooterBalance(
                                        allAccounts
                                    )
                                )
                                updateBaseAppState(
                                    AppActions.BaseAppScreenAction.UpdateFooterRest(
                                        allAccounts
                                    )
                                )
                                updateBaseAppState(
                                    AppActions.BaseAppScreenAction.UpdateAccounts(
                                        allAccounts
                                    )
                                )
                            }
                        }
                        AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(
                            PresentationDataModel("My accounts")
                        )
                    }
                }
            }
            //MyAccount  screen ----------------------------------------------------------------
            is AppActions.MyAccountScreenAction -> {
                updateMyAccountScreenState(action)
                when (action) {
                    is AppActions.MyAccountScreenAction.InitScreen -> {
                        myAccountScreenJob = scope.launch {
                            getAllAccountsInteractor.getAllAccounts().collect {
                                updateMyAccountScreenState(
                                    AppActions.MyAccountScreenAction.UpdateAccountList(it)
                                )
                            }
                        }
                    }
                    is AppActions.MyAccountScreenAction.CloseScreen -> {
                        myAccountScreenJob?.cancel()
                    }
                }
            }


            //MyAccount detail screen ----------------------------------------------------------------
            is AppActions.MyAccountDetailScreenAction -> {
                updateMyAccountDetailScreenState(action)
                when (action) {
                    is AppActions.MyAccountDetailScreenAction.InitScreen -> {
                        updateBaseAppState(
                            AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(
                                PresentationDataModel("Detail")
                            )
                        )
                        myAccountDetailScreenJob = scope.launch {
                            getAllOperationsForAccountIdInteractor.getAllOperationsForAccountId(
                                action.selectedAccount.id
                            ).collect {
                                //todo pass in dao
                                val filteredList = it.filter {
                                    it.emitDate.month.compareTo(Calendar.getInstance().time.month) == 0
                                }
                                updateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations(
                                        filteredList
                                    )
                                )
                                updateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateAccountBalance(
                                        PresentationDataModel(action.selectedAccount.accountBalance.toString())
                                    )
                                )
                                updateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateAccountRest(
                                        PresentationDataModel(
                                           (action.selectedAccount.accountOverdraft+ action.selectedAccount.accountBalance).toString()
                                        )
                                    )
                                )
                                updateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateAccountName(
                                        PresentationDataModel(
                                            action.selectedAccount.name,
                                            action.selectedAccount.id
                                        ) )
                                )
                            }
                        }
                    }
                    is AppActions.MyAccountDetailScreenAction.CloseScreen->{
                        myAccountDetailScreenJob?.cancel()
                    }
                }
            }

            //Add Operation pop up -----------------------------------------------------------------------------------------
            is AppActions.AddOperationPopUpAction -> {
                updateAddOperationPopUpState(action)
                when (action) {
                    is AppActions.AddOperationPopUpAction.InitPopUp ->
                        getAllCategoriesJob = scope.launch {
                            getAllCategoriesInteractor.getAllCategoriesToDataUiModelList().collect {
                                updateAddOperationPopUpState(
                                    AppActions.AddOperationPopUpAction.UpdateCategoriesList(
                                        it
                                    )
                                )
                            }
                        }

                    is AppActions.AddOperationPopUpAction.ExpandTransferOption ->
                        getAllAccountsJob = scope.launch {
                            getAllAccountsInteractor.getAllAccountsToPresentationDataModel()
                                .collect {
                                    updateAddOperationPopUpState(
                                        AppActions.AddOperationPopUpAction.UpdateAccountList(
                                            it
                                        )
                                    )
                                }
                        }

                    is AppActions.AddOperationPopUpAction.ClosePopUp -> {
                        getAllAccountsJob?.cancel()
                        getAllCategoriesJob?.cancel()
                    }
                }
            }

            //Delete Account PopUp ----------------------------------------------------------------------------
            is AppActions.DeleteAccountAction -> {
                updateDeleteAccountPopUpState(action)
                when (action) {
                    is AppActions.DeleteAccountAction.InitPopUp -> {
                        scope.launch {
                            updateDeleteAccountPopUpState(
                                AppActions.DeleteAccountAction.UpdateAccountToDelete(
                                    getAccountForIdInteractor.getAccountForId(action.accountName.objectIdReference)
                                )
                            )
                        }
                    }
                    is AppActions.DeleteAccountAction.DeleteAccount -> {
                        scope.launch {
                            deleteAccountInteractor.deleteAccount(deleteAccountUiState.accountToDelete)
                        }
                    }
                }
            }
            //Add Account PopUp -------------------------------------------------------------------------
            is AppActions.AddAccountPopUpAction -> {
                updateAddAccountPopUpState(action)
                when (action) {

                    is AppActions.AddAccountPopUpAction.AddNewAccount -> {
                        if (!addAccountPopUpUiState.isNameError)
                            scope.launch {
                                try {
                                    addNewAccountInteractor.addNewAccount(
                                        AccountModel(
                                            name = action.accountName.stringValue,
                                            accountBalance = try {
                                                action.accountBalance.stringValue.toDouble()
                                            } catch (e: NumberFormatException) {
                                                0.0
                                            },
                                            accountOverdraft = try {
                                                action.accountOverdraft.stringValue.toDouble()
                                            } catch (e: NumberFormatException) {
                                                0.0
                                            }
                                        )
                                    )
                                    updateAddAccountPopUpState(
                                        AppActions.AddAccountPopUpAction.ClosePopUp
                                    )
                                } catch (e: Exception) {
                                    //todo catch error
                                }
                            }
                    }
                }
            }
            //DeleteOperationPopUp ----------------------------------------------------------------
            is AppActions.DeleteOperationPopUpAction ->{
                updateDeleteOperationPoUpState(action)
                when(action){
                    is AppActions.DeleteOperationPopUpAction.InitPopUp->{
                        updateDeleteOperationPoUpState(
                            AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete(action.operationToDelete)
                        )

                    }
                    is AppActions.DeleteOperationPopUpAction.DeleteOperation-> {
                        scope.launch {
                            deleteOperationInteractor.deleteOperation(action.operationToDelete)

                        }
                    }
                }
            }

        }
    }

    //implement update component state -------------------------------------------------------------------------------------

    private fun updateBaseAppState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateBaseAppScreenVmState(action)
        )
    }

    private fun updateAddOperationPopUpState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateAddOperationPopUpState(action)
        )
    }

    private fun updateDeleteAccountPopUpState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateDeleteAccountPopUpState(action)
        )
    }

    private fun updateAddAccountPopUpState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateAddAccountPopUpState(action)
        )
    }

    private fun updateMyAccountScreenState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateMyAccountScreenState(action)
        )
    }

    private fun updateMyAccountDetailScreenState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateMyAccountDetailScreenState(action)
        )
    }

    private fun updateDeleteOperationPoUpState(action: UiAction){
        store.dispatch(
            AppActions.GlobalAction.UpdateDeleteOperationPopUpState(action)
        )
    }
}