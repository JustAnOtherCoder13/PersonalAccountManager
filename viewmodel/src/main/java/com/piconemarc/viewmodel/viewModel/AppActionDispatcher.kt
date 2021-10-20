package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
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
        when (action) {

            //BaseAppScreen -----------------------------------------------------------------------------------
            is AppActions.GlobalAction.UpdateBaseAppScreenVmState ->
                updateBaseAppState(action)

            //Add pop up -----------------------------------------------------------------------------------------
            is AppActions.AddOperationPopUpAction -> {
                updateAddPopState(action)
                when (action){
                    is AppActions.AddOperationPopUpAction.InitPopUp ->
                        getAllCategoriesJob = scope.launch {
                            getAllCategoriesInteractor.getAllCategoriesToDataUiModelList().collect {
                                updateAddPopState(AppActions.AddOperationPopUpAction.UpdateCategoriesList(it))
                            }
                        }

                    is AppActions.AddOperationPopUpAction.ExpandTransferOption ->
                        getAllAccountsJob = scope.launch {
                            getAllAccountsInteractor.getAllAccountsToPresentationDataModel().collect {
                                updateAddPopState(AppActions.AddOperationPopUpAction.UpdateAccountList(it))
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
        store.add(subscriber)
        store.dispatch(
            AppActions.GlobalAction.UpdateBaseAppScreenVmState(action)
        )
    }

    private fun updateAddPopState(action: UiAction) {
        store.add(subscriber)
        store.dispatch(
            AppActions.GlobalAction.UpdateAddPopUpState(action)
        )
    }
}