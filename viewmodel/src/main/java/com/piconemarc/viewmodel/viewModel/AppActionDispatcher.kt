package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.StoreSubscriber
import com.piconemarc.viewmodel.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppActionDispatcher @Inject constructor(
    override val store: DefaultStore<ViewModelInnerStates.GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor
) : ActionDispatcher<UiAction, ViewModelInnerStates.GlobalVmState>() {

    override val subscriber: StoreSubscriber<ViewModelInnerStates.GlobalVmState> =
        AppSubscriber().appStoreSubscriber

    //flow job
    private var getAllAccountsJob: Job? = null
    private var getAllCategoriesJob: Job? = null

    override fun dispatchAction(action: UiAction) {
        store.add(subscriber)
        when (action) {

            //BaseAppScreen -----------------------------------------------------------------------------------
            is AppActions.BaseAppScreenAction -> {
                updateBaseAppState(action)
                when (action){
                    is AppActions.BaseAppScreenAction.InitScreen ->
                        getAllAccountsJob = scope.launch {
                            getAllAccountsInteractor.getAllAccounts().collect { allAccounts ->
                                var allAccountBalance  = 0.0
                                var allAccountRest = 0.0
                                allAccounts.forEach {
                                    allAccountBalance += it.accountBalance
                                    allAccountRest += it.accountOverdraft+allAccountBalance
                                }
                                updateBaseAppState(AppActions.BaseAppScreenAction.UpdateFooterBalance(
                                    PresentationDataModel(
                                        stringValue = allAccountBalance.toString()
                                    )
                                ))
                                updateBaseAppState(AppActions.BaseAppScreenAction.UpdateFooterRest(
                                    PresentationDataModel(
                                        stringValue = allAccountRest.toString()
                                    )
                                ))
                                updateBaseAppState(AppActions.BaseAppScreenAction.UpdateAccounts(allAccounts))
                            }}
                }
            }

            //Add pop up -----------------------------------------------------------------------------------------
            is AppActions.AddOperationPopUpAction -> {
                updateAddPopUpState(action)
                when (action){
                    is AppActions.AddOperationPopUpAction.InitPopUp ->
                        getAllCategoriesJob = scope.launch {
                            getAllCategoriesInteractor.getAllCategoriesToDataUiModelList().collect {
                                updateAddPopUpState(AppActions.AddOperationPopUpAction.UpdateCategoriesList(it))
                            }
                        }

                    is AppActions.AddOperationPopUpAction.ExpandTransferOption ->
                        getAllAccountsJob = scope.launch {
                            getAllAccountsInteractor.getAllAccountsToPresentationDataModel().collect {
                                updateAddPopUpState(AppActions.AddOperationPopUpAction.UpdateAccountList(it))
                            }}

                    is AppActions.AddOperationPopUpAction.ClosePopUp ->{
                        getAllAccountsJob?.cancel()
                        getAllCategoriesJob?.cancel()
                    }
                }
            }
        }
    }

    //implement actions -------------------------------------------------------------------------------------

    private fun updateBaseAppState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateBaseAppScreenVmState(action)
        )
    }

    private fun updateAddPopUpState(action: UiAction) {
        store.dispatch(
            AppActions.GlobalAction.UpdateAddPopUpState(action)
        )
    }
}